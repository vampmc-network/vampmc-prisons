package me.reklessmitch.mitchprisonscore.mitchcells.configs;

import com.massivecraft.massivecore.store.Entity;
import lombok.Getter;
import me.reklessmitch.mitchprisonscore.mitchcells.object.Cell;

import java.util.*;

@Getter
public class CellConf extends Entity<CellConf> {

    public static CellConf i = new CellConf();
    public static CellConf get() { return i; }

    int maxCellSize = 10;
    private String prefix = "§7[§6Cells§7] ";
    private Map<String, Cell> cells = new HashMap<>();

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
}
