package me.reklessmitch.mitchprisonscore.mitchcells.cmds.cellcmds;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import me.reklessmitch.mitchprisonscore.mitchcells.cmds.CellCommands;
import me.reklessmitch.mitchprisonscore.mitchcells.configs.CellConf;
import me.reklessmitch.mitchprisonscore.mitchcells.object.Cell;
import org.bukkit.entity.Player;

public class CmdCellInvite extends CellCommands {

    public CmdCellInvite(){
        this.addAliases("invite");
        this.addParameter(TypePlayer.get(), "player");
    }

    @Override
    public void perform() throws MassiveException {
        Cell cell = CellConf.get().getCellByMember(me.getUniqueId());
        if(cell == null){
            msg("§cYou are not in a cell");
            return;
        }
        if(!cell.getAllHigherUps().contains(me.getUniqueId())){
            msg("§cYou are not a higher up in this cell");
            return;
        }
        Player player = this.readArg();
        if(cell.getInvites().contains(player.getUniqueId())){
            msg("§cPlayer is already invited");
            return;
        }
        cell.getInvites().add(player.getUniqueId());
        msg("§aInvited §c" + player.getName() + "§a to your cell");
    }

}
