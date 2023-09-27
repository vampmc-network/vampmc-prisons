package me.reklessmitch.mitchprisonscore.mitchboosters.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.util.ItemBuilder;
import me.reklessmitch.mitchprisonscore.mitchboosters.configs.BoosterPlayer;
import me.reklessmitch.mitchprisonscore.mitchboosters.objects.Booster;
import me.reklessmitch.mitchprisonscore.utils.LangConf;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class BoosterGUI extends ChestGui {

    BoosterPlayer player;

    public BoosterGUI(UUID p) {
        this.player = BoosterPlayer.get(p);
        player.changed();
        setInventory(Bukkit.createInventory(null, 54, LangConf.get().getBoosterGuiTitle()));
        setAutoclosing(false);
        setAutoremoving(true);
        setSoundOpen(null);
        setSoundClose(null);
        refresh();
        add();
    }

    public void refresh() {
        getInventory().clear();
        setUpActiveBoosters();
        getInventory().setItem(53, getUpgradeBoosterItem());
        int boosterSlot = 0;
        for(Booster booster : player.getBoosters()){
            getInventory().setItem(boosterSlot, booster.getBoosterItem());
            setAction(boosterSlot, event -> {
                event.setCancelled(true);
                player.activateBooster(booster);
                refresh();
                return true;
            });
            boosterSlot++;
        }
    }

    private void setUpActiveBoosters() {
        Booster tokenBooster = player.getActiveTokenBooster();
        Booster moneyBooster = player.getActiveMoneyBooster();
        Booster beaconBooster = player.getActiveBeaconBooster();

        if(tokenBooster != null) {boostersPos(45, tokenBooster);}
        if(moneyBooster != null) {boostersPos(47, moneyBooster);}
        if(beaconBooster != null) {boostersPos(49, beaconBooster);}
    }

    private void boostersPos(int slot, Booster booster){
        getInventory().setItem(slot, booster.getBoosterItem());
        setAction(slot, event -> {
            event.setCancelled(true);
            player.deactivateBooster(booster);
            refresh();
            return true;
        });
    }

    private ItemStack getUpgradeBoosterItem() {
        setAction(53, event -> {
            event.setCancelled(true);
            if(player.combineBoosters()) {
                refresh();
            }
            return true;
        });
        return new ItemBuilder(Material.MAGMA_BLOCK).displayname("&aCombine Boosters").lore("&7Click to combine your boosters").build();
    }

    public void open(Player player) {
        player.openInventory(getInventory());
    }
}
