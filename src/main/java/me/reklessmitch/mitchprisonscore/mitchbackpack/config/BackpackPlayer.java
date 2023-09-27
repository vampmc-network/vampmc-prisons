package me.reklessmitch.mitchprisonscore.mitchbackpack.config;

import com.massivecraft.massivecore.mixin.MixinTitle;
import com.massivecraft.massivecore.store.SenderEntity;
import com.massivecraft.massivecore.util.ItemBuilder;
import lombok.Getter;
import lombok.Setter;
import me.reklessmitch.mitchprisonscore.colls.BackPackPlayerColl;
import me.reklessmitch.mitchprisonscore.mitchboosters.configs.BoosterPlayer;
import me.reklessmitch.mitchprisonscore.mitchboosters.objects.Booster;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PetPlayer;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PetType;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PickaxeConf;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.utils.EnchantType;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.CurrencyUtils;
import org.bukkit.Bukkit;
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

    private boolean messages = true;
    private int skinID = 0;
    private long currentLoad = 0;
    private long capacity = 100;
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

    public void setMessages(boolean messages) {
        this.messages = messages;
        this.changed();
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
        }
        changed();
    }

    public void addSlot(long amount){
        capacity += amount;
        getPlayer().sendMessage("§aYou have upgraded your backpack by " + amount + " slots!");
        changed();
        set();
    }

    public double getPetBooster(){
        PetPlayer pet = PetPlayer.get(getId());
        return pet.getActivePet() == PetType.MONEY ? pet.getPet(PetType.MONEY).getPetBooster() : 0;
    }

    public void sell() {
        boolean boostActivated = false;
        long startAmount = currentLoad;
        PPickaxe ppickaxe = PPickaxe.get(getId());
        double greedMulti = ppickaxe.getEnchants().get(EnchantType.GREED) / 1000.0;
        if(greedMulti != 0) {
            startAmount *= 1 + greedMulti;
        }
        int boostLevel = ppickaxe.getEnchants().get(EnchantType.BOOST);
        if(boostLevel != 0 &&
                new SecureRandom().nextDouble(1) < PickaxeConf.get().getEnchantByType(EnchantType.BOOST).getProcChance(boostLevel)){
            startAmount *= 2;
            boostActivated = true;
        }
        double petBooster = getPetBooster();
        if(petBooster > 0){
            startAmount *= 1 + petBooster;
        }

        Booster booster = BoosterPlayer.get(getId()).getActiveMoneyBooster();
        if(booster != null){
            startAmount *= booster.getMultiplier();
        }
        int rank = ProfilePlayer.get(getId()).getRank();
        startAmount *= rank;
        ProfilePlayer.get(getId()).getCurrency("money").add(startAmount);
        if(messages) {
            getPlayer().sendMessage("§a-------------------------" +
                    "\n§aYou have sold §e" + currentLoad + " §aitems for §e" + CurrencyUtils.format(startAmount) + " §amoney" +
                    "\n§aRank Multiplier (+" + rank + ")" +
                    (booster != null ? "\n§aBooster Multiplier (+" + booster.getMultiplier() + ")" : "") +
                    (boostActivated ? "\n§aBoost Multiplier (2x)" : "") +
                    (greedMulti > 0 ? "\n§aGreed Multiplier (+" + greedMulti / 1000.0 + ")" : "") +
                    (petBooster > 0 ? "\n§aPet Multiplier (+" + petBooster + ")" : "") +
                    "\n§a-------------------------");
        }

        if(ppickaxe.isAutoRankup()){
            ProfilePlayer.get(getId()).rankUpMax();
        }
        currentLoad = 0;
        changed();
        set();
    }

    public long getCost(int amountToBuy) { // @REDO THIS
        BackpackConf conf = BackpackConf.get();
        long costPerSlot = conf.getSlotPriceIncreasePerSize();
        return amountToBuy * costPerSlot;
    }

    public int getMaxPurchasable() {
        long tokens = ProfilePlayer.get(getId()).getCurrency("token").getAmount();
        BackpackConf conf = BackpackConf.get();
        int costPerSlot = conf.getSlotPriceIncreasePerSize();
        return (int) (tokens / costPerSlot);
    }

    private String getSkinName(){
        return switch(skinID){
            case 10001 -> "duck";
            case 10002 -> "cat";
            case 2192001 -> "sadshark";
            case 2192002 -> "happyshark";
            default -> "penguin";
        };
    }
    public void setSkin(int customDataModel) {
        skinID = customDataModel;
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "hud popup" + getPlayer().getName() + " " + getSkinName() + " 2100000000");
        set();
        changed();
    }
}
