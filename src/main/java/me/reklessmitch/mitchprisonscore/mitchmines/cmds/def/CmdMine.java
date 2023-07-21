package me.reklessmitch.mitchprisonscore.mitchmines.cmds.def;

import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import me.reklessmitch.mitchprisonscore.mitchmines.cmds.MineCommands;
import me.reklessmitch.mitchprisonscore.mitchmines.cmds.admin.CmdMineIncrease;
import me.reklessmitch.mitchprisonscore.mitchmines.guis.MineGUI;

public class CmdMine extends MineCommands {

    private static final CmdMine i = new CmdMine();
    public static CmdMine get() { return i; }

    public CmdMineGO go = new CmdMineGO();
    public CmdResetMine reset = new CmdResetMine();
    public CmdSetBlock setBlock = new CmdSetBlock();
    public CmdMineIncrease increase = new CmdMineIncrease();

    public CmdMine(){
        this.addAliases("mine");
        this.addRequirements(RequirementIsPlayer.get());
        this.addChild(go);
        this.addChild(reset);
        this.addChild(setBlock);
        this.addChild(increase);
    }

    @Override
    public void perform() {
        new MineGUI(me).open();
    }

}
