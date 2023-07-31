package me.reklessmitch.mitchprisonscore.mitchpickaxe.enchants;

import com.massivecraft.massivecore.util.ItemBuilder;
import lombok.Getter;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.utils.DisplayItem;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.utils.EnchantType;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class Enchant {
    private EnchantType type;
    private DisplayItem displayItem;
    private int levelRequired;
    private int maxLevel;
    private double baseProcRate;
    private double procRateIncreasePerLevel;
    private long baseCost;
    private long costIncreasePerLevel;

    public Enchant(EnchantType type, Material material, String name, List<String> description, int customModelData, int slot, int levelRequired, int maxLevel, double baseProcRate, double procRateIncreasePerLevel, long baseCost, long costIncreasePerLevel) {
        this.type = type;
        this.displayItem = new DisplayItem(material, name, description, customModelData, slot);
        this.levelRequired = levelRequired;
        this.maxLevel = maxLevel;
        this.baseProcRate = baseProcRate;
        this.procRateIncreasePerLevel = procRateIncreasePerLevel;
        this.baseCost = baseCost;
        this.costIncreasePerLevel = costIncreasePerLevel;

    }

    /**
     * @deprecated use {@link #getCost(int, int)} instead
     */
    @Deprecated(since="1.1", forRemoval=true)
    public int getCostOld(int currentLevel, int amountToBuy) {
        int cost = 0;
        for (int i = 0; i < amountToBuy; i++) {
            cost += baseCost + (currentLevel * costIncreasePerLevel);
            currentLevel++;
        }
        return cost;
    }

    public long getCost(int currentLevel, int amountToBuy) {
        long firstTerm = baseCost + (currentLevel * costIncreasePerLevel);
        long lastTerm = baseCost + ((currentLevel + amountToBuy - 1) * costIncreasePerLevel);
        return (amountToBuy * (firstTerm + lastTerm)) / 2;
    }

    private int recursiveCost(int currentLevel, long maxBudget, int amount, long cost, int maxLevel) {
        if (currentLevel >= maxLevel) {
            return amount;
        }
        cost += getCost(currentLevel, 1);
        amount++;
        currentLevel++;
        if(cost > maxBudget) {
            return amount - 1;
        }
        return recursiveCost(currentLevel, maxBudget, amount, cost, maxLevel);
    }


    public int getMaxAmount(int currentLevel, long maxBudget, int maxLevel) {
        return recursiveCost(currentLevel, maxBudget, 0, 0, maxLevel);
    }

    public double getProcChance(int currentLevel) {
        return baseProcRate + (currentLevel * procRateIncreasePerLevel);
    }

    public ItemStack getEnchantGuiItem(PPickaxe pickaxe) {
        return new ItemBuilder(displayItem.getMaterial())
                .displayname(displayItem.getItemName())
                .lore(displayItem.getItemLore().stream().map(s -> {
                    if (s == null) return "";
                    s = s.replace("{level}", String.valueOf(pickaxe.getEnchants().get(type)));
                    s = s.replace("{maxlevel}", String.valueOf(maxLevel));
                    s = s.replace("{cost}", String.valueOf(getCost(pickaxe.getEnchants().get(type), 1)));
                    s = s.replace("{levelRequired}", ProfilePlayer.get(pickaxe.getPlayer()).getRank() >= levelRequired
                            ? "" : "§7Rank Required: §c" + levelRequired);
                    return s;
                }).toList())
                .modelData(displayItem.getCustomModelData())
                .flag(ItemFlag.HIDE_ATTRIBUTES)
                .build();
    }

    public ItemStack getEnchantGuiToggleItem(PPickaxe pickaxe) {
        return new ItemBuilder(displayItem.getMaterial()).displayname(displayItem.getItemName())
                .lore(List.of("§a ", pickaxe.getEnchantToggle().get(type) ? "§aEnabled" : "§cDisabled")).flag(ItemFlag.HIDE_ATTRIBUTES).build();
    }

    public ItemStack getEnchantMessageToggleItem(PPickaxe pickaxe) {
        return new ItemBuilder(displayItem.getMaterial()).displayname(displayItem.getItemName())
                .lore(List.of("§a ", pickaxe.getEnchantMessages().get(type) ? "§aEnabled" : "§cDisabled")).flag(ItemFlag.HIDE_ATTRIBUTES).build();
    }
}
