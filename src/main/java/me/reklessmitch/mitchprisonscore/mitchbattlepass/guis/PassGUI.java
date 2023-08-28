package me.reklessmitch.mitchprisonscore.mitchbattlepass.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.util.ItemBuilder;
import me.reklessmitch.mitchprisonscore.mitchbattlepass.configs.PassConf;
import me.reklessmitch.mitchprisonscore.mitchbattlepass.configs.PassPlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PassGUI extends ChestGui {

    Player player;

    public PassGUI(Player player){
        this.player = player;
        setInventory(Bukkit.createInventory(null, 27, ":offset_-28::pass:"));
        setupInventory();
        add();
    }

    private void setUpUnpaid(ProfilePlayer profile, PassPlayer pp, int cost) {
        getInventory().setItem(15, new ItemBuilder(Material.BARRIER).displayname("§aBuy Battle Pass")
                .lore("§7Click to buy the battle pass", "", "§aCost: §f" + cost).build());
        setAction(15, event -> {
            if(profile.getCurrency("credits").getAmount() < cost){
                player.sendMessage("§cYou do not have enough credits to buy the battle pass");
                return true;
            }
            profile.getCurrency("credits").take(cost);
            profile.changed();
            player.sendMessage("§aYou have bought the battle pass");
            pp.setPremium(true);
            pp.changed();
            return true;
        });
    }

    private void setUpPaid() {
        getInventory().setItem(15, new ItemBuilder(Material.DIAMOND).displayname("§aPaid Rewards").lore("§7Click to claim paid rewards").build());
        setAction(15, event -> {
            PassPlayer.get(player.getUniqueId()).claimPaidRewards();
            return true;
        });
        getInventory().setItem(26, new ItemBuilder(Material.EMERALD).displayname("§aClaim All").lore("§7Click to claim all rewards").build());
        setAction(26, event -> {
            PassPlayer.get(player.getUniqueId()).claimAllRewards();
            return true;
        });
    }

    private void setupInventory() {
        int cost = PassConf.get().getCreditsToBuyPremium();
        ProfilePlayer profile = ProfilePlayer.get(player.getUniqueId());
        PassPlayer pp = PassPlayer.get(player.getUniqueId());
        if(!pp.isPremium()) {
            setUpUnpaid(profile, pp, cost);
        } else {
            setUpPaid();
        }
        getInventory().setItem(11, new ItemBuilder(Material.IRON_INGOT).displayname("§aFree Rewards").lore("§7Click to claim free rewards").build());
        setAction(11, event -> {
            PassPlayer.get(player.getUniqueId()).claimFreeRewards();
            return true;
        });

    }

    public void open() {
        player.openInventory(getInventory());
    }
}
