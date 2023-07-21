package me.reklessmitch.mitchprisonscore.mitchmines.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.util.ItemBuilder;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.MineConf;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.PlayerMine;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.currency.MitchCurrency;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MineGUI extends ChestGui {

    Player player;
    PlayerMine playerMine;

    public MineGUI(Player player){
        this.player = player;
        this.playerMine = PlayerMine.get(player.getUniqueId());
        setInventory(Bukkit.createInventory(null, 36, "Mine GUI"));
        init();
        add();
    }

    private void addMineInformationItem(){
        getInventory().setItem(4, new ItemBuilder(playerMine.getBlock()).displayname("§aMine Information")
            .lore("§cMine Size: §f" + playerMine.getSize(),
                    "§cMine Block: §f" + playerMine.getBlock().name(),
                    "§cMine Booster: §f" + playerMine.getBooster()).build());

    }

    private void addMineGOItem(){
        getInventory().setItem(11, new ItemBuilder(playerMine.getBlock()).displayname("§aMine GO")
            .lore("§cTeleport to your mine").build());
        setAction(11, event -> {
            playerMine.teleport();
            return true;
        });
    }

    private void addUpgradeBoosterItem(){
        int cost = MineConf.get().getMineBoosterCost();
        int maxLevel = MineConf.get().getMineBoosterMax();
        if(playerMine.getBooster() >= maxLevel){
            getInventory().setItem(13, new ItemBuilder(Material.BARRIER).displayname("§aUpgrade Booster")
                .lore("§7Upgrade your mine booster by 1x", "", "§cMax Level Reached").build());
            return;
        }else{
            getInventory().setItem(13, new ItemBuilder(Material.DIAMOND).displayname("§aUpgrade Booster")
                .lore("§7Upgrade your mine booster by 1x", "§cCost: §f" + cost).build());
        }

        setAction(13, event -> {
            ProfilePlayer profile = ProfilePlayer.get(player.getUniqueId());
            MitchCurrency currency = profile.getCurrency("credits");
            if(currency.getAmount() >= cost){
                currency.take(cost);
                playerMine.addBooster(1);
                addUpgradeBoosterItem();
            }else {
                player.sendMessage("§cYou do not have enough credits to upgrade your mine booster");
            }
            return true;
        });
    }

    private void addResetItem(){
        getInventory().setItem(15, new ItemBuilder(Material.BARRIER).displayname("§aReset Mine")
            .lore("§cReset your mine").build());
        setAction(15, event -> {
            playerMine.reset();
            return true;
        });
    }

    private void init() {
        addMineInformationItem();
        addMineGOItem();
        addUpgradeBoosterItem();
        addResetItem();
    }

    public void open() {
        player.openInventory(getInventory());
    }
}
