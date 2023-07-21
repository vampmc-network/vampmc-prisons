package me.reklessmitch.mitchprisonscore.mitchmines.cmds.def;

import me.reklessmitch.mitchprisonscore.mitchmines.cmds.MineCommands;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.PlayerMine;
import org.bukkit.entity.Player;

public class CmdResetMine extends MineCommands {

    public CmdResetMine(){
        this.addAliases("reset");
    }

    @Override
    public void perform() {
        Player player = (Player) sender;
        PlayerMine.get(player.getUniqueId()).reset();
    }
}
