package me.reklessmitch.mitchprisonscore.mitchcells.cmds.cellcmds;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import me.reklessmitch.mitchprisonscore.mitchcells.cmds.CellCommands;
import me.reklessmitch.mitchprisonscore.mitchcells.configs.CellConf;
import me.reklessmitch.mitchprisonscore.mitchcells.object.Cell;

public class CmdCreate extends CellCommands {

    public CmdCreate(){
        this.addParameter(TypeString.get(), "cellname");
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        String cellName = this.readArg();
        CellConf conf = CellConf.get();
        if(conf.getCellNames().contains(cellName)){
            msg("<b>Cell already exists");
            return;
        }
        if(conf.getAllMembers().contains(me.getUniqueId())){
            msg("<b>You are already in a cell");
            return;
        }
        conf.getCells().put(cellName, new Cell(cellName, me.getUniqueId()));
        msg("<g>Cell created " + cellName);
    }
}
