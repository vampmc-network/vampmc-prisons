package me.reklessmitch.mitchprisonscore.mitchpets.util;

import com.massivecraft.massivecore.util.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DisplayItem {

    Material material;
    String itemName;
    List<String> itemLore;
    int customModelData;
    int slot;

    public DisplayItem(Material material, String itemName, List<String> itemLore, int customModelData, int slot) {
        this.material = material;
        this.itemName = itemName;
        this.itemLore = itemLore;
        this.customModelData = customModelData;
        this.slot = slot;
    }

    public ItemStack getGuiItem(int level) {
        List<String> newLore = new ArrayList<>(itemLore);
        newLore.replaceAll(lore -> lore.replace("{level}", level + ""));
        return new ItemBuilder(material).displayname(itemName).lore(newLore).modelData(customModelData).unbreakable(true)
                .flag(ItemFlag.HIDE_ATTRIBUTES)
                .build();
    }

    public ItemStack getGuiItem() {
        return new ItemBuilder(material).displayname(itemName).lore(itemLore).modelData(customModelData).unbreakable(true)
                .flag(ItemFlag.HIDE_ATTRIBUTES)
                .build();
    }


}
