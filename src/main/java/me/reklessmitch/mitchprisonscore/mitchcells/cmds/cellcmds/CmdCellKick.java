package me.reklessmitch.mitchprisonscore.mitchcells.cmds.cellcmds;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import me.reklessmitch.mitchprisonscore.mitchcells.cmds.CellCommands;
import me.reklessmitch.mitchprisonscore.mitchcells.configs.CellConf;
import me.reklessmitch.mitchprisonscore.mitchcells.object.Cell;
import org.bukkit.entity.Player;

public class CmdCellKick extends CellCommands {

    public CmdCellKick(){
        this.addAliases("kick");
        this.addParameter(TypePlayer.get(), "player");
    }

    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        Cell cell = CellConf.get().getCellByMember(me.getUniqueId());
        if(cell == null){
            msg("<b>You are not in a cell");
            return;
        }
        if(!cell.getAllHigherUps().contains(me.getUniqueId())){
            msg("<b>You are not a higher up in this cell");
            return;
        }
        if(me.getUniqueId() == player.getUniqueId()){
            msg("<b>You cannot kick yourself");
            return;
        }
        if(!cell.getAllMembers().contains(player.getUniqueId())){
            msg("<b>Player is not in your cell");
            return;
        }
        if(cell.getOwner().equals(me.getUniqueId())){
            kickPlayer(player, cell);
        } else if (cell.getMembers().contains(player.getUniqueId()) && cell.getOfficers().contains(me.getUniqueId())) {
            kickPlayer(player, cell);
        } else {
            msg("<b>You cannot kick this player as they're equal or higher rank than you");
        }
    }
    private void kickPlayer(Player player, Cell cell) {
        cell.removePlayer(player.getUniqueId());
        msg("<b> You have kicked " + player.getName() + " from your cell");
        player.sendMessage("<b>You have been kicked from your cell");
    }
}
