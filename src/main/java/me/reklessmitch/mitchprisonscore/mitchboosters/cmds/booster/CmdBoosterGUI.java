package me.reklessmitch.mitchprisonscore.mitchboosters.cmds.booster;

import me.reklessmitch.mitchprisonscore.mitchboosters.cmds.BoosterCommands;
import me.reklessmitch.mitchprisonscore.mitchboosters.guis.BoosterGUI;
import org.bukkit.entity.Player;

public class CmdBoosterGUI extends BoosterCommands {

    public CmdBoosterGUI() {
        this.addAliases("gui");
    }

    @Override
    public void perform() {
        Player player = me.getPlayer();
        assert player != null;
        new BoosterGUI(player.getUniqueId()).open(player);
    }
}
