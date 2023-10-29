package me.reklessmitch.mitchprisonscore.mitchcells.cmds.cellcmds;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import me.reklessmitch.mitchprisonscore.mitchcells.cmds.CellCommands;
import me.reklessmitch.mitchprisonscore.mitchcells.configs.CellConf;
import me.reklessmitch.mitchprisonscore.mitchcells.object.Cell;

public class CmdCellCreate extends CellCommands {

    public CmdCellCreate(){
        this.addAliases("create");
        this.addParameter(TypeString.get(), "cellname");
    }

    @Override
    public void perform() throws MassiveException {
        String cellName = this.readArg();
        CellConf conf = CellConf.get();
        if(conf.getCellNames().contains(cellName.toUpperCase())){
            msg("§cCell already exists");
            return;
        }
        if(conf.getAllPlayersInCells().contains(me.getUniqueId())){
            msg("§cYou are already in a cell");
            return;
        }
        conf.getCells().put(cellName.toUpperCase(), new Cell(cellName, me.getUniqueId()));
        msg("§aCell created " + cellName);
        conf.changed();
    }
}
