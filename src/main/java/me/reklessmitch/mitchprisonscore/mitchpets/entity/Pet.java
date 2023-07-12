package me.reklessmitch.mitchprisonscore.mitchpets.entity;

import lombok.Getter;
import me.reklessmitch.mitchprisonscore.mitchpets.util.DisplayItem;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Pet {

    private final PetType type;
    private int level;
    private int xp;

    public Pet(PetType type) {
        this.type = type;
        this.level = 1;
        this.xp = 0;
    }

    public void addLevel(int level) {
        this.level += level;
    }

    public void addXp(int xp) {
        this.xp += xp;
    }

    public void removeLevel(int level) {
        this.level -= level;
    }

    public void removeXp(int xp) {
        this.xp -= xp;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    // Change this to be placeholder not this dogshit
    public ItemStack getDisplayItem(PPlayer player) {
        DisplayItem displayItem = PetConf.get().getPetDisplayItems().get(type);
        ItemStack item = new ItemStack(displayItem.getMaterial());
        List<String> lore = new ArrayList<>(displayItem.getItemLore());
        lore.add("§7Level: " + level);
        lore.add("§7Xp: " + xp);
        if(player.activePet == this.getType()) {
            lore.add("§a");
            lore.add("§aActive");
        }

        item.getItemMeta().setLore(lore);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(displayItem.getCustomModelData());
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(displayItem.getItemName());
        item.setItemMeta(meta);
        return item;
    }

    public double getPetBooster(){
        return PetConf.get().getPetBoosts().get(type).getBoost(level);
    }

    public void spawn(){

    }
}
