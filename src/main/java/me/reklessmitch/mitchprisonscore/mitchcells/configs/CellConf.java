package me.reklessmitch.mitchprisonscore.mitchcells.configs;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import lombok.Getter;
import me.reklessmitch.mitchprisonscore.mitchcells.object.Cell;
import me.reklessmitch.mitchprisonscore.mitchcells.object.DisplayItemCommand;
import org.bukkit.Material;

import java.util.*;

@Getter
@EditorName("config")
public class CellConf extends Entity<CellConf> {

    public static CellConf i = new CellConf();
    public static CellConf get() { return i; }

    int maxCellSize = 10;
    private Map<String, Cell> cells = new HashMap<>();
    private int size = 36;
    private List<DisplayItemCommand> guiItems = List.of(new DisplayItemCommand(
            Material.BEACON, "&aCreate Cell", List.of("&7Click to create a cell using /cell create {name}"), 1, 0,
            ""
    ));


    public List<String> getCellNames(){
        return new ArrayList<>(cells.keySet());
    }

    public Cell getCellByMember(UUID playerID){
        for(Cell cell : cells.values()){
            if(cell.getMembers().contains(playerID) || cell.getOwner().equals(playerID)){
                return cell;
            }
        }
        return null;
    }

    public Set<UUID> getAllPlayersInCells(){
        Set<UUID> players = new HashSet<>();
        cells.values().forEach(cell -> players.addAll(cell.getAllMembers()));
        return players;
    }

    public Cell getCellByName(String cellName) {
        return cells.get(cellName.toUpperCase());
    }
}
