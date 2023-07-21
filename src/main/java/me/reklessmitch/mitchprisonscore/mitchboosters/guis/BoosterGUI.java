package me.reklessmitch.mitchprisonscore.mitchboosters.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.util.ItemBuilder;
import me.reklessmitch.mitchprisonscore.mitchboosters.configs.BoosterPlayer;
import me.reklessmitch.mitchprisonscore.mitchboosters.objects.Booster;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BoosterGUI extends ChestGui {

    BoosterPlayer player;

    public BoosterGUI(Player player) {
        this.player = BoosterPlayer.get(player.getUniqueId());
        setInventory(Bukkit.createInventory(null, 54, "Boosters"));
        setAutoclosing(false);
        setAutoremoving(true);
        setSoundOpen(null);
        setSoundClose(null);
        refresh();
        add();
    }

    public void refresh() {
        getInventory().setItem(50, getUpgradeBoosterItem());
        int boosterSlot = 10;
        for(Booster booster : player.getBoosters()){
            getInventory().setItem(boosterSlot, booster.getBoosterItem());
            boosterSlot++;
        }
    }

    private ItemStack getUpgradeBoosterItem() {
        setAction(50, event -> {
            event.setCancelled(true);
            player.combineBoosters();
            refresh();
            return true;
        });
        return new ItemBuilder(Material.MAGMA_BLOCK).displayname("&aCombine Boosters").lore("&7Click to combine your boosters").build();
    }

    public void open() {
        player.getPlayer().openInventory(getInventory());
    }
}
