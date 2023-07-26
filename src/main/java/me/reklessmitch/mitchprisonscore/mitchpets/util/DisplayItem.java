package me.reklessmitch.mitchprisonscore.mitchpets.util;

import com.massivecraft.massivecore.util.ItemBuilder;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class DisplayItem {

    Material material;
    String itemName;
    List<String> itemLore;
    int customModelData;

    public DisplayItem(Material material, String itemName, List<String> itemLore, int customModelData) {
        this.material = material;
        this.itemName = itemName;
        this.itemLore = itemLore;
        this.customModelData = customModelData;
    }

    public ItemStack getGuiItem(int level) {
        itemLore.replaceAll(lore -> lore.replace("{level}", "" + level));
        return new ItemBuilder(material).displayname(itemName).lore(itemLore).modelData(customModelData).unbreakable(true)
                .flag(ItemFlag.HIDE_ATTRIBUTES)
                .build();
    }


}
