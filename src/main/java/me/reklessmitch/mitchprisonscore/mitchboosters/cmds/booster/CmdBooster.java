package me.reklessmitch.mitchprisonscore.mitchboosters.cmds.booster;

import me.reklessmitch.mitchprisonscore.mitchboosters.cmds.BoosterCommands;

public class CmdBooster extends BoosterCommands {

    private static final CmdBooster i = new CmdBooster();
    public static CmdBooster get() { return i; }

    private final CmdGive cmdGive = new CmdGive();

    public CmdBooster(){
        this.addAliases("booster");
        this.addChild(this.cmdGive);
    }
}
