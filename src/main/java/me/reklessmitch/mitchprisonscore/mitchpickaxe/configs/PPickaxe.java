package me.reklessmitch.mitchprisonscore.mitchpickaxe.configs;

import com.massivecraft.massivecore.store.SenderEntity;
import lombok.Getter;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.colls.PPickaxeColl;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.enchants.Enchant;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.utils.DisplayItem;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.utils.EnchantType;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Getter
public class PPickaxe extends SenderEntity<PPickaxe> {

    DisplayItem pickaxe = new DisplayItem(Material.DIAMOND_PICKAXE, "§bPickaxe", List.of("§eEnchants"), 0, 4);
    Map<EnchantType, Integer> enchants = setEnchants();
    Map<EnchantType, Boolean> enchantToggle = setEnchantToggle();

    private Map<EnchantType, Boolean> setEnchantToggle() {
        Map<EnchantType, Boolean> enchantTogglesList = new EnumMap<>(EnchantType.class);
        PickaxeConf.get().enchants.keySet().forEach(enchant -> enchantTogglesList.put(enchant, true));
        return enchantTogglesList;
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
        List<String> lore = new ArrayList<>(List.of("§eEnchants"));
        lore.add("§7 ");
        enchants.forEach((enchantType, level) -> {
            if(level == 0) return;
            Enchant e = PickaxeConf.get().getEnchantByType(enchantType);
            if (e == null) return;
            lore.add(e.getDisplayItem().getItemName() + "§f: " + level);
        });
        pickaxe.setItemLore(lore);
        this.changed();
    }

    private Map<EnchantType, Integer> setEnchants(){
        Map<EnchantType, Integer> enchantList = new EnumMap<>(EnchantType.class);
        PickaxeConf.get().enchants.keySet().forEach(enchant -> enchantList.put(enchant, 0));
        return enchantList;
    }

    public void givePickaxe() {
        getPlayer().getInventory().setItem(0, pickaxe.getGuiItem(getPlayer().getUniqueId()));
    }

    public void setSkin(int customDataModel) {
        pickaxe.setCustomModelData(customDataModel);
        changed();
        givePickaxe();
    }

    public void toggleEnchant(EnchantType enchantType){
        boolean toggle = enchantToggle.get(enchantType);
        getPlayer().sendMessage("§aToggled " + enchantType + ": " + (toggle ? "§cDISABLED" : "§aENABLED"));
        enchantToggle.replace(enchantType, !toggle);
        changed();
    }
}
