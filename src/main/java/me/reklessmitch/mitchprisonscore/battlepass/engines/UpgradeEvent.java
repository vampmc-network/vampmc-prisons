package me.reklessmitch.mitchprisonscore.battlepass.engines;

import com.massivecraft.massivecore.Engine;
import me.reklessmitch.mitchprisonscore.battlepass.configs.PassConf;
import me.reklessmitch.mitchprisonscore.battlepass.configs.PassPlayer;
import me.reklessmitch.mitchprisonscore.battlepass.events.UpgradeBattlePassEvent;
import me.reklessmitch.mitchprisonscore.mitchbackpack.engine.BlocksToBackpack;
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
