package me.reklessmitch.mitchprisonscore.mitchpickaxe.utils;

import com.massivecraft.massivecore.util.ItemBuilder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@Setter
public class DisplayItem {

    private Material material;
    private String itemName;
    private List<String> itemLore;
    private int customModelData;
    private int slot;

    public DisplayItem(Material material, String itemName, List<String> itemLore, int customModelData, int slot) {
        this.material = material;
        this.itemName = itemName;
        this.itemLore = itemLore;
        this.customModelData = customModelData;
        this.slot = slot;
    }

    public ItemStack getGuiItem(int efficiencyLevel){
        return new ItemBuilder(material).displayname(itemName).lore(itemLore).modelData(customModelData).unbreakable(true)
            .flag(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE)
            .enchant(Enchantment.DIG_SPEED, efficiencyLevel)
            .build();
    }



}
