package me.reklessmitch.mitchprisonscore.mitchcells.cmds.cellcmds;


import me.reklessmitch.mitchprisonscore.mitchcells.cmds.CellCommands;
import me.reklessmitch.mitchprisonscore.mitchcells.configs.CellConf;
import me.reklessmitch.mitchprisonscore.mitchcells.object.Cell;

public class CmdCellLeave extends CellCommands {

    public CmdCellLeave(){
        this.addAliases("leave");
    }

    @Override
    public void perform() {
        CellConf conf = CellConf.get();
        Cell cell = conf.getCellByMember(me.getUniqueId());
        if(cell == null){
            msg("§cYou are not in a cell");
            return;
        }
        if(cell.getOwner().equals(me.getUniqueId())){
            msg("§cYou are the owner of this cell, you must disband it to leave");
            return;
        }
        msg("§aYou have left your cell");
        cell.removePlayer(me.getUniqueId(), me);
        conf.changed();

    }
}