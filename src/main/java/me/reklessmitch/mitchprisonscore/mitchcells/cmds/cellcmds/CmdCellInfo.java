package me.reklessmitch.mitchprisonscore.mitchcells.cmds.cellcmds;

import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.util.IdUtil;
import me.reklessmitch.mitchprisonscore.mitchcells.cmds.CellCommands;
import me.reklessmitch.mitchprisonscore.mitchcells.configs.CellConf;
import me.reklessmitch.mitchprisonscore.mitchcells.object.Cell;

public class CmdCellInfo extends CellCommands {

    public CmdCellInfo(){
        this.addAliases("info");
    }

    @Override
    public void perform() {
        CellConf conf = CellConf.get();
        Cell cell = conf.getCellByMember(me.getUniqueId());
        if(cell == null){
            msg("<b>You are not in a cell");
            return;
        }
        msg("§7§m------------------§r §6§lCell Info §7§m------------------" +
                "\n§7Cell Name: §6" + cell.getName() +
                "\n§7Cell Owner: §6" + IdUtil.getOfflinePlayer(cell.getOwner()).getName() +
                "\n§7Cell Members: §6" + cell.getMembers().size() +
                "\n§7Beacons: §6" + cell.getBeacons() +
                "\n§7§m-------------------------------------------------");
    }
}
