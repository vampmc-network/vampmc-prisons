package me.reklessmitch.mitchprisonscore.mitchbattlepass.engines;

import com.massivecraft.massivecore.Engine;
import me.reklessmitch.mitchprisonscore.mitchbattlepass.configs.PassConf;
import me.reklessmitch.mitchprisonscore.mitchbattlepass.configs.PassPlayer;
import me.reklessmitch.mitchprisonscore.mitchbattlepass.events.UpgradeBattlePassEvent;
import org.bukkit.event.EventHandler;

public class UpgradeEvent extends Engine {

    public static UpgradeEvent i = new UpgradeEvent();
    public static UpgradeEvent get(){
        return i;
    }

    @EventHandler
    public void onUpgrade(UpgradeBattlePassEvent e){
        PassPlayer pp = PassPlayer.get(e.getPlayer().getUniqueId());
        if(pp.getLevel() == 100) return;
        if(PassConf.get().getBlocksPerLevel().get(pp.getLevel() + 1) <= e.getBlocksBroken()){
            pp.addLevel();
            e.getPlayer().sendMessage("You have upgraded your battlepass to level " + pp.getLevel());
        }
    }


}
