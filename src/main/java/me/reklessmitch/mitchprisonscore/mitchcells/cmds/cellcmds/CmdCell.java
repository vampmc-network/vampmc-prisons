package me.reklessmitch.mitchprisonscore.mitchcells.cmds.cellcmds;

import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import me.reklessmitch.mitchprisonscore.mitchcells.cmds.CellCommands;
import me.reklessmitch.mitchprisonscore.mitchcells.guis.CellGUI;

public class CmdCell extends CellCommands {

    private static CmdCell i = new CmdCell();
    public static CmdCell get() { return i; }

    public CmdAddBeacons addBeacons = new CmdAddBeacons();
    public CmdCellCreate cellCreate = new CmdCellCreate();
    public CmdCellInvite cellInvite = new CmdCellInvite();
    public CmdCellJoin cellJoin = new CmdCellJoin();
    public CmdCellLeave cellLeave = new CmdCellLeave();
    public CmdCellDisband cellDisband = new CmdCellDisband();
    public CmdCellKick cellKick = new CmdCellKick();
    public CmdCellInfo cellInfo = new CmdCellInfo();
    public CmdCellTop cmdCellTop = new CmdCellTop();


    public CmdCell(){
        this.addRequirements(RequirementIsPlayer.get());
        this.addAliases("cell", "gang", "team");
        this.addChild(addBeacons);
        this.addChild(cellCreate);
        this.addChild(cellInvite);
        this.addChild(cellJoin);
        this.addChild(cellLeave);
        this.addChild(cellDisband);
        this.addChild(cellKick);
        this.addChild(cellInfo);
        this.addChild(cmdCellTop);
    }

    @Override
    public void perform(){
        new CellGUI().open(me);
    }

}
