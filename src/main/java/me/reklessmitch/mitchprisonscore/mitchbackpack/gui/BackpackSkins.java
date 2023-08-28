package me.reklessmitch.mitchprisonscore.mitchbackpack.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.util.ItemBuilder;
import me.reklessmitch.mitchprisonscore.mitchbackpack.config.BackpackPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BackpackSkins extends ChestGui {

    Player player;

    public BackpackSkins(Player player){
        setInventory(Bukkit.createInventory(null, 9, "Backpack Skins"));
        this.player = player;
        setupInventory();
        setAutoclosing(false);
        setSoundOpen(null);
        setSoundClose(null);
        add();
    }

    private void getBackpackSkinItem(String name, String lore, int customDataModel, int slot){
        ItemStack item = new ItemBuilder(Material.DRAGON_EGG).displayname(name).lore(lore).modelData(customDataModel).build();
        getInventory().setItem(slot, item);
        this.setAction(slot, event -> {
            event.setCancelled(true);
            if(!player.hasPermission("mitchprisonscore.backpack." + customDataModel)){
                player.sendMessage("§cYou do not have permission to use this skin!");
                return false;
            }
            BackpackPlayer bp = BackpackPlayer.get(player.getUniqueId());
            if(bp.getSkinID() == customDataModel){
                player.sendMessage("§cYou already have this skin selected!");
                return false;
            }
            player.sendMessage("§aYou have selected the " + name + " backpack skin!");
            bp.setSkin(customDataModel);
            player.closeInventory();
            return true;
        });
    }

    private void setupInventory() {
        getBackpackSkinItem("§aDefault", "§7The default pickaxe skin.", 0, 0);
        getBackpackSkinItem("§bPenguin Backpack", "§b ", 10000, 1);
        getBackpackSkinItem("§bDuck Backpack", "§b ", 10001, 2);
        getBackpackSkinItem("§bCat Backpack", "§b ", 10002, 3);
        getBackpackSkinItem("§bHappy Shark Backpack", "§b ", 2192002, 4);
        getBackpackSkinItem("§bSad Shark Backpack", "§b ", 2192001, 5);
    }

    public void open(){
        player.openInventory(getInventory());
    }

}
