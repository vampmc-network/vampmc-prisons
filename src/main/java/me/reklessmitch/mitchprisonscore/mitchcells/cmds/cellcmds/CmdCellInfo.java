package me.reklessmitch.mitchprisonscore.mitchcells.cmds.cellcmds;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import com.massivecraft.massivecore.util.IdUtil;
import me.reklessmitch.mitchprisonscore.mitchcells.cmds.CellCommands;
import me.reklessmitch.mitchprisonscore.mitchcells.configs.CellConf;
import me.reklessmitch.mitchprisonscore.mitchcells.object.Cell;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CmdCellInfo extends CellCommands {

    public CmdCellInfo(){
        this.addAliases("info");
        this.addParameter(TypePlayer.get(), "player");
    }

    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        CellConf conf = CellConf.get();
        Cell cell = conf.getCellByMember(player.getUniqueId());
        if(cell == null){
            msg("<b>You are not in a cell");
            return;
        }
        msg("§7§m------------§r §6§lCell Info §7§m------------" +
                "\n§7Cell Name: §6" + cell.getName() +
                "\n§7Cell Owner: §6" + IdUtil.getOfflinePlayer(cell.getOwner()).getName() +
                "\n§7Cell Members: §6" + getAllMembers(cell) +
                "\n§7Beacons: §6" + cell.getBeacons() +
                "\n§7§m----------------------------------");
    }

    // turn Set<List> of cell.getAllMembers() into a string with commas
    public String getAllMembers(Cell cell) {
        StringBuilder allMembers = new StringBuilder();
        for(UUID playerID : cell.getAllMembers()){
            if(playerID.equals(cell.getOwner())) continue; // skip owner (already displayed)
            OfflinePlayer player = IdUtil.getOfflinePlayer(playerID);
            allMembers.append(player.isOnline() ? "§a" + player.getName() + " " : "§7" + player.getName() + " ");
        }
        return allMembers.toString();
    }

}
