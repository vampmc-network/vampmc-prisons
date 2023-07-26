package me.reklessmitch.mitchprisonscore.mitchrankup.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchrankup.config.RankupConf;
import me.reklessmitch.mitchprisonscore.mitchrankup.object.RankupReward;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class RankupRewardsGUI extends ChestGui {

    Player player;

    public RankupRewardsGUI(Player player){
        this.player = player;
        setInventory(Bukkit.createInventory(null, 54, "Rankup Rewards"));
        add();
        RankupConf.get().getRankupRewards().forEach(this::setup);
        setAutoclosing(false);
        setAutoremoving(true);
    }

    private void setup(int levelRequired, RankupReward item){
        ProfilePlayer pp = ProfilePlayer.get(player.getUniqueId());
        if(pp.getClaimedRewards().contains(levelRequired)){
            getInventory().setItem(item.getSlot(), item.getRewardItem(Material.RED_WOOL));
        } else if (pp.getRank() < levelRequired) {
            getInventory().setItem(item.getSlot(), item.getRewardItem(Material.RED_STAINED_GLASS_PANE));
        }else {
            getInventory().setItem(item.getSlot(), item.getRewardItem());
            setAction(item.getSlot(), event -> {
                item.getCommands().forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName())));
                pp.getClaimedRewards().add(levelRequired);
                pp.changed();
                setup(levelRequired, item);
                return true;
            });
        }
    }

    public void open(){
        player.openInventory(getInventory());
    }
}
