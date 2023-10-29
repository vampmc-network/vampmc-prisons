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
        cellName = cellName.toLowerCase();
        CellConf conf = CellConf.get();
        if(conf.getAllPlayersInCells().contains(me.getUniqueId())){
            msg("§cYou are already in a cell");
            return;
        }
        Cell cell = conf.getCellByName(cellName);
        if(cell == null){
            msg("§cCell does not exist");
            return;
        }
        if(cell.getMembers().size() >= conf.getMaxCellSize()){
            msg("§cCell is full");
            return;
        }
        if(cell.getInvites().contains(me.getUniqueId())){
            cell.getMembers().add(me.getUniqueId());
            msg("§aJoined cell " + cellName);
            cell.getInvites().remove(me.getUniqueId());
            conf.changed();
        }else{
            msg("§cYou are not invited to this cell");
        }


    }


}
