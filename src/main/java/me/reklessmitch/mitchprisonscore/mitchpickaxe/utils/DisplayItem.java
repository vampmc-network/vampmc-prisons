package me.reklessmitch.mitchprisonscore.mitchpickaxe.utils;

import lombok.Getter;
import lombok.Setter;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
// TODO: 03/07/2023 Improve this, XSeries have a good item serializer.
public class DisplayItem {
    // These Cannot be final because MCORE needs to be able to serialize them
    private Material material;
    private String itemName;
    private List<String> itemLore;
    int customModelData;
    int slot;

    public DisplayItem(Material material, String itemName, List<String> itemLore, int customModelData, int slot) {
        this.material = material;
        this.itemName = itemName;
        this.itemLore = itemLore;
        this.customModelData = customModelData;
        this.slot = slot;
    }

    public ItemStack getGuiItem(UUID player){
        ItemStack i = new ItemStack(material);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(itemName);
        meta.setLore(itemLore.stream().map(s -> {
            if (s == null) return "";
            return s;
        }).toList());

        meta.setCustomModelData(customModelData);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        i.setItemMeta(meta);
        i.addUnsafeEnchantment(Enchantment.DIG_SPEED, PPickaxe.get(player).getEnchants().get(EnchantType.EFFICIENCY));

        return i;
    }



}
