package me.reklessmitch.mitchprisonscore.mitchprofiles.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.util.ItemBuilder;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilesConf;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class SelectJoinMessageGUI extends ChestGui{

    Inventory inv;
    ProfilePlayer profilePlayer;
    Player player;

    public SelectJoinMessageGUI(Player player) {
        this.inv = Bukkit.createInventory(null, 45, "§aSelect Join Message");
        this.profilePlayer = ProfilePlayer.get(player);
        this.player = player;
        add();
        refresh();
    }

    private void refresh(){
        inv.setItem(4, new ItemBuilder(Material.DIAMOND, 1, "§aCurrent Join Message").lore(profilePlayer.getJoinMessage()).build());
        int i = 9;
        for(String message : ProfilesConf.get().getJoinMessages()){
            inv.setItem(i, new ItemBuilder(Material.PAPER, 1, "§a" + message).build());
            setAction(i, event -> {
                event.setCancelled(true);
                profilePlayer.setJoinMessage(message);
                player.sendMessage("§aJoin message set to: " + message);
                refresh();
                profilePlayer.changed();
                return true;
            });
            i++;

        }
    }


    public void open(){
        player.openInventory(inv);
    }

}
