package me.reklessmitch.mitchprisonscore.mitchbattlepass.engines;

import com.massivecraft.massivecore.Engine;
import me.reklessmitch.mitchprisonscore.mitchbattlepass.configs.PassConf;
import me.reklessmitch.mitchprisonscore.mitchbattlepass.configs.PassPlayer;
import me.reklessmitch.mitchprisonscore.mitchbattlepass.events.BlocksMinedEvent;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import org.bukkit.event.EventHandler;

public class UpgradeEvent extends Engine {

    public static UpgradeEvent i = new UpgradeEvent();
    public static UpgradeEvent get(){
        return i;
    }

    @EventHandler(ignoreCancelled = true)
    public void onUpgrade(BlocksMinedEvent e){
        PassPlayer pp = PassPlayer.get(e.getPlayer().getUniqueId());
        if(pp.getLevel() == PassConf.get().getMaxLevel()) return;
        long blocksBroken = PPickaxe.get(e.getPlayer().getUniqueId()).getBlocksBroken();
        if(pp.getLevel() == PassConf.get().getMaxLevel()) return;
        long blocksPerLevel = PassConf.get().getBlocksPerLevel().get(pp.getLevel() + 1);
        if(blocksPerLevel <= blocksBroken){
            pp.addLevel();
            e.getPlayer().sendMessage("§aYou have upgraded your battlepass to level " + pp.getLevel());
        }
    }
}
