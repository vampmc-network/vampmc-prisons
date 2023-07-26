package me.reklessmitch.mitchprisonscore.mitchprofiles.object;

import com.massivecraft.massivecore.util.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class ShopItem {

    private int slot;
    private int cost;
    private Material material;
    private String name;
    private String description;
    private List<String> commands;
    private List<String> lore;

    public ShopItem(int slot, int cost, Material material, String name, String description, List<String> commands, List<String> lore) {
        this.slot = slot;
        this.cost = cost;
        this.material = material;
        this.name = name;
        this.description = description;
        this.commands = commands;
        this.lore = lore;
    }

    public ItemStack getGuiItem(){
        lore.replaceAll(s -> s.replace("%cost%", "" + cost));
        return new ItemBuilder(material).displayname(name).lore(lore).build();
    }
}
