package me.reklessmitch.mitchprisonscore.mitchmines.cmds.def;

import me.reklessmitch.mitchprisonscore.mitchmines.cmds.MineCommands;
import me.reklessmitch.mitchprisonscore.mitchmines.guis.BlockGUI;

public class CmdSetBlock extends MineCommands {

    public CmdSetBlock(){
        this.addAliases("setblock");
    }

    @Override
    public void perform() {
        new BlockGUI(me).open();
    }
}
