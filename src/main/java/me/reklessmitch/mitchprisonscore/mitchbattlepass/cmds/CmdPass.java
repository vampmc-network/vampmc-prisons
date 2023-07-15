package me.reklessmitch.mitchprisonscore.mitchbattlepass.cmds;

import me.reklessmitch.mitchprisonscore.mitchbattlepass.guis.PassGUI;

public class CmdPass extends BattlePassCommands{

    private static final CmdPass i = new CmdPass();
    public static CmdPass get() { return i; }

    public CmdPass(){
        this.addAliases("pass");
    }

    @Override
    public void perform() {
        new PassGUI(me).open();
    }
}

