package me.reklessmitch.mitchprisonscore.mitchcells.cmds.cellcmds;


import me.reklessmitch.mitchprisonscore.mitchcells.cmds.CellCommands;
import me.reklessmitch.mitchprisonscore.mitchcells.configs.CellConf;
import me.reklessmitch.mitchprisonscore.mitchcells.object.Cell;

public class CmdCellDisband extends CellCommands {

    public CmdCellDisband(){
        this.addAliases("disband");
    }

    @Override
    public void perform() {
        Cell cell = CellConf.get().getCellByMember(me.getUniqueId());
        if(cell == null){
            msg("§bYou are not in a cell");
            return;
        }
        if(!cell.getOwner().equals(me.getUniqueId())){
            msg("§bYou are not the owner of this cell");
            return;
        }
        cell.disband();
        msg("§bYou have disbanded your cell");
    }
}
