package me.reklessmitch.mitchprisonscore.mitchpickaxe.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.util.ItemBuilder;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class TogglesMainGUI extends ChestGui {

    private final Player player;

    public TogglesMainGUI(Player player){
        this.player = player;
        this.setInventory(Bukkit.createInventory(null, 27, "Toggles"));
        add();
        addItems();
    }

    private void addItems(){
        // - Loot and Key finder for Virtualkey or in inventory  :red_circle:
        //- Message Toggles :red_circle:
        //- Enchant Toggles :red_circle:
        //- Enchant Sounds :red_circle:

        getInventory().setItem(10, new ItemBuilder(Material.TRIPWIRE_HOOK).displayname("&cToggle Virtual Key").build());
        setAction(10, event -> {
            PPickaxe.get(player.getUniqueId()).toggleVirtualKey();
            return true;
        });

        getInventory().setItem(11, new ItemBuilder(Material.PAPER).displayname("&cMessage Toggles").build());
        setAction(11, event -> {
            new TogglesGUI(player, 0).open();
            return true;
        });
        getInventory().setItem(12, new ItemBuilder(Material.COMPARATOR).displayname("&cEnchant Toggles").build());
        setAction(12, event -> {
            new TogglesGUI(player, 1).open();
            return true;
        });
        getInventory().setItem(13, new ItemBuilder(Material.JUKEBOX).displayname("&cEnchant Sound Toggles").build());
        setAction(13, event -> {
            new TogglesGUI(player, 2).open();
            return true;
        });
        getInventory().setItem(14, new ItemBuilder(Material.DIAMOND).displayname("&cAuto Rankup").build());
        setAction(14, event -> {
            PPickaxe.get(player.getUniqueId()).toggleAutoRankup();
            return true;
        });
    }

    public void open() {
        player.openInventory(getInventory());
    }
}
