package me.reklessmitch.mitchprisonscore.battlepass.cmds;

import me.reklessmitch.mitchprisonscore.battlepass.guis.PassGUI;

public class CmdPass extends BattlePassCommands{

    public CmdPass(){
        this.addAliases("pass");
    }

    @Override
    public void perform() {
        new PassGUI(me).open();

    }

}
