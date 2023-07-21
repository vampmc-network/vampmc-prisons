package me.reklessmitch.mitchprisonscore.mitchmines.cmds.def;

import me.reklessmitch.mitchprisonscore.mitchmines.cmds.MineCommands;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.PlayerMine;

public class CmdMineGO extends MineCommands {

    public CmdMineGO(){
        this.addAliases("go");
    }

    @Override
    public void perform() {
        PlayerMine.get(me.getUniqueId()).teleport();
    }
}
