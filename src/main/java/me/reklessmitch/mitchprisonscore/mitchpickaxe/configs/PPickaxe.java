package me.reklessmitch.mitchprisonscore.mitchpickaxe.configs;

import com.massivecraft.massivecore.store.SenderEntity;
import lombok.Getter;
import me.reklessmitch.mitchprisonscore.colls.PPickaxeColl;
import me.reklessmitch.mitchprisonscore.mitchbattlepass.events.BlocksMinedEvent;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.enchants.Enchant;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.utils.DisplayItem;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.utils.EnchantType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Getter
public class PPickaxe extends SenderEntity<PPickaxe> {

    private DisplayItem pickaxe = new DisplayItem(Material.DIAMOND_PICKAXE, "§bPickaxe",
            List.of("§eEnchants"), 0, 4);
    private long rawBlocksBroken = 0;
    private long blocksBroken = 0;
    private Map<EnchantType, Integer> enchants = setEnchants();
    private Map<EnchantType, Boolean> enchantToggle = setEnchantToggle();
    private Map<EnchantType, Boolean> enchantMessages = setEnchantToggle();

    private Map<EnchantType, Boolean> setEnchantToggle() {
        Map<EnchantType, Boolean> enchantTogglesList = new EnumMap<>(EnchantType.class);
        PickaxeConf.get().enchants.keySet().forEach(enchant -> enchantTogglesList.put(enchant, true));
        return enchantTogglesList;
    }

    public void addRawBlockBroken(){
        rawBlocksBroken++;
    }

    public void addBlockBroken(long amount){
        blocksBroken += amount;
        BlocksMinedEvent e = new BlocksMinedEvent(getPlayer(), amount);
        Bukkit.getPluginManager().callEvent(e);

    }

    public static PPickaxe get(Object oid) {
        return PPickaxeColl.get().get(oid);
    }

    @Override
    public PPickaxe load(@NotNull PPickaxe that)
    {
        super.load(that);
        return this;
    }

    public void updatePickaxe(){
        List<String> lore = new ArrayList<>(List.of("§3§lEnchants"));
        lore.add("§7 ");
        enchants.forEach((enchantType, level) -> {
            if(level == 0) return;
            Enchant e = PickaxeConf.get().getEnchantByType(enchantType);
            if (e == null) return;
            lore.add("§b| §3" + e.getDisplayItem().getItemName() + "§f: " + level);
        });
        pickaxe.setItemLore(lore);
        givePickaxe();
        this.changed();
    }

    public ItemStack getPickaxeGuiItem(){
        return pickaxe.getGuiItem(enchants.get(EnchantType.EFFICIENCY));
    }

    private Map<EnchantType, Integer> setEnchants(){
        Map<EnchantType, Integer> enchantList = new EnumMap<>(EnchantType.class);
        PickaxeConf.get().enchants.keySet().forEach(enchant -> {
            if(enchant == EnchantType.EFFICIENCY){
                enchantList.put(enchant, 10);
                return;
            }
            enchantList.put(enchant, 0);
        });
        return enchantList;
    }

    public void givePickaxe() {
        getPlayer().getInventory().setItem(0, getPickaxeGuiItem());
    }

    public void setSkin(int customDataModel) {
        pickaxe.setCustomModelData(customDataModel);
        // do the popup listener for the pickaxe

        changed();
        givePickaxe();
    }

    public void toggleEnchant(EnchantType enchantType){
        boolean toggle = enchantToggle.get(enchantType);
        getPlayer().sendMessage("§aToggled " + enchantType + ": " + (toggle ? "§cDISABLED" : "§aENABLED"));
        enchantToggle.replace(enchantType, !toggle);
        changed();
    }

    public void toggleEnchantMessage(EnchantType type) {
        boolean toggle = enchantMessages.get(type);
        getPlayer().sendMessage("§aToggled " + type + " messages : " + (toggle ? "§cDISABLED" : "§aENABLED"));
        enchantMessages.replace(type, !toggle);
        changed();
    }
}
