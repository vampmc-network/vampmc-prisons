package me.reklessmitch.mitchprisonscore.mitchprofiles.object;

import com.massivecraft.massivecore.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class WardrobeItem {

    private Material material;
    private String name;
    private List<String> lore;
    private int customData;

    public WardrobeItem(Material material, String name, List<String> lore, int customData) {
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.customData = customData;
    }

    public WardrobeItem(ItemStack item){
        this.material = item.getType();
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return;
        this.name = meta.getDisplayName();
        this.lore = meta.getLore();
        this.customData = meta.getCustomModelData();
    }

    public ItemStack getGuiItem(){
        return new ItemBuilder(material)
                .displayname(name)
                .lore(" " + lore)
                .modelData(customData)
                .build();
    }
}
