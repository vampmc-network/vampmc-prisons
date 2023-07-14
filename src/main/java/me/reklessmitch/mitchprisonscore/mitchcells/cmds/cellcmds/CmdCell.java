package me.reklessmitch.mitchprisonscore.mitchcells.cmds.cellcmds;

import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import me.reklessmitch.mitchprisonscore.mitchcells.cmds.CellCommands;

public class CmdCell extends CellCommands {

    public CmdAddBeacons addBeacons = new CmdAddBeacons();
    public CmdCellInvite cellInvite = new CmdCellInvite();
    public CmdCellJoin cellJoin = new CmdCellJoin();
    public CmdCellLeave cellLeave = new CmdCellLeave();
    public CmdCellDisband cellDisband = new CmdCellDisband();
    public CmdCellKick cellKick = new CmdCellKick();
    public CmdCellInfo cellInfo = new CmdCellInfo();

    public CmdCell(){
        this.addRequirements(RequirementIsPlayer.get());
        this.addAliases("cell", "gang", "team");
        this.addChild(addBeacons);
        this.addChild(cellInvite);
        this.addChild(cellJoin);
        this.addChild(cellLeave);
        this.addChild(cellDisband);
        this.addChild(cellKick);
        this.addChild(cellInfo);
    }

}
