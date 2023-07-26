package me.reklessmitch.mitchprisonscore.mitchrankup.object;

import com.massivecraft.massivecore.util.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class RankupReward {

    private Material material;
    private String itemName;
    private List<String> itemLore;
    private List<String> commands;
    int customModelData;
    int unlockLevel;
    int slot;

    public RankupReward(Material material, String itemName, List<String> itemLore, List<String> commands,
                        int customModelData, int unlockLevel, int slot) {
        this.material = material;
        this.itemName = itemName;
        this.itemLore = itemLore;
        this.commands = commands;
        this.customModelData = customModelData;
        this.unlockLevel = unlockLevel;
        this.slot = slot;
    }

    public ItemStack getRewardItem() {
        return new ItemBuilder(material).displayname(itemName).lore(itemLore).modelData(customModelData).build();
    }

    public ItemStack getRewardItem(Material newMaterial) {
        return new ItemBuilder(newMaterial).displayname(itemName).lore(itemLore).modelData(customModelData).build();
    }
}
