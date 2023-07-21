package me.reklessmitch.mitchprisonscore.mitchbackpack.config;

import com.massivecraft.massivecore.mixin.MixinActionbar;
import com.massivecraft.massivecore.mixin.MixinTitle;
import com.massivecraft.massivecore.store.SenderEntity;
import com.massivecraft.massivecore.util.ItemBuilder;
import lombok.Getter;
import lombok.Setter;
import me.reklessmitch.mitchprisonscore.colls.BackPackPlayerColl;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PPlayer;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PetType;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PickaxeConf;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.utils.EnchantType;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;

@Getter
@Setter
public class BackpackPlayer extends SenderEntity<BackpackPlayer> {

    public static BackpackPlayer get(Object oid) {
        return BackPackPlayerColl.get().get(oid);
    }

    @Override
    public BackpackPlayer load(@NotNull BackpackPlayer that) {
        super.load(that);
        return this;
    }

    private int skinID = 0;
    private int currentLoad = 0;
    private int capacity = 100;
    private boolean autoSell = false;

    public void set() {
        getPlayer().getInventory().setItem(7, getBackpackItem());
    }

    public ItemStack getBackpackItem() {
        return new ItemBuilder(Material.DRAGON_EGG)
                .displayname("§6Backpack")
                .lore("§7Size: §e" + capacity, "§7Autosell: " + (autoSell ? "§aEnabled" : "§cDisabled"))
                .modelData(skinID)
                .build();
    }

    public void add(long amount) {
        if(capacity <= amount + currentLoad){
            currentLoad = capacity;
            if(autoSell){
                sell();
                return;
            }
            MixinTitle.get().sendTitleMsg(getPlayer(), 0, 20, 0, "§cBackpack is full!", "§7Sell your items with §e/sell");
        }else{
            currentLoad += amount;
            MixinActionbar.get().sendActionbarMessage(getPlayer(), "§a" + currentLoad + "/" + capacity);
        }
        changed();
        set();
    }

    public void addSlot(int amount){
        capacity += amount;
        getPlayer().sendMessage("§aYou have upgraded your backpack by " + amount + " slots!");
        changed();
        set();
    }

    public double getPetBooster(){
        PPlayer pet = PPlayer.get(getId());
        return pet.getActivePet() == PetType.MONEY ? pet.getPet(PetType.MONEY).getPetBooster() : 0;
    }

    public void sell() {
        boolean boostActivated = false;
        int startAmount = currentLoad;
        PPickaxe ppickaxe = PPickaxe.get(getId());
        double greedMulti = ppickaxe.getEnchants().get(EnchantType.GREED) / 1000.0;
        if(greedMulti != 0) {
            startAmount *= 1 + greedMulti;
        }
        int boostLevel = ppickaxe.getEnchants().get(EnchantType.BOOST);
        if(boostLevel == 0 && new SecureRandom().nextDouble(1) > PickaxeConf.get().getEnchantByType(EnchantType.BOOST).getProcChance(boostLevel)){
            startAmount *= 2;
            boostActivated = true;
        }
        double petBooster = getPetBooster();
        if(petBooster > 0){
            startAmount *= 1 + petBooster;
        }

        ProfilePlayer.get(getId()).getCurrency("money").add(startAmount);
        getPlayer().sendMessage("§a-------------------------" +
                                "\n§aYou have sold §e" + currentLoad + " §aitems for §e" + startAmount + " §amoney" +
                (boostActivated ? "\n§aBoost Multiplier (2x)" : "") +
                        (greedMulti > 0 ? "\n§aGreed Multiplier (+" + greedMulti / 1000.0 + ")" : "") +
                (petBooster > 0 ? "\n§aPet Multiplier (+" + petBooster + ")" : "" ) +
                                "\n§a-------------------------");

        currentLoad = 0;
        changed();
        set();
    }

    public int getCost(int amountToBuy) { // @REDO THIS
        BackpackConf conf = BackpackConf.get();
        int costPerSlot = conf.getSlotPriceIncreasePerSize();
        return amountToBuy * costPerSlot;
    }

    public int getMaxPurchasable() {
        long tokens = ProfilePlayer.get(getId()).getCurrency("token").getAmount();
        BackpackConf conf = BackpackConf.get();
        int costPerSlot = conf.getSlotPriceIncreasePerSize();
        return (int) (tokens / costPerSlot);
    }

    public void setSkin(int customDataModel) {
        skinID = customDataModel;
        set();
        changed();
    }
}
