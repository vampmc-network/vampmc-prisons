package me.reklessmitch.mitchprisonscore.mitchcells.cmds.cellcmds;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import me.reklessmitch.mitchprisonscore.mitchcells.cmds.CellCommands;
import me.reklessmitch.mitchprisonscore.mitchcells.configs.CellConf;
import me.reklessmitch.mitchprisonscore.mitchcells.object.Cell;

public class CmdCellJoin extends CellCommands {


    public CmdCellJoin(){
        this.addAliases("join");
        this.addParameter(TypeString.get(), "cellname");
    }

    @Override
    public void perform() throws MassiveException {
        String cellName = this.readArg();
        CellConf conf = CellConf.get();
        if(conf.getAllPlayersInCells().contains(me.getUniqueId())){
            msg("<b>You are already in a cell");
            return;
        }
        Cell cell = conf.getCells().get(cellName);
        if(cell == null){
            msg("<b>Cell does not exist");
            return;
        }
        if(cell.getMembers().size() >= conf.getMaxCellSize()){
            msg("<b>Cell is full");
            return;
        }
        if(cell.getInvites().contains(me.getUniqueId())){
            cell.getMembers().add(me.getUniqueId());
            msg("<g>Joined cell " + cellName);
            cell.getInvites().remove(me.getUniqueId());
        }else{
            msg("<b>You are not invited to this cell");
        }


    }


}
