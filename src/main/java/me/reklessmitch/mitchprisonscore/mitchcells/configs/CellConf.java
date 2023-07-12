package me.reklessmitch.mitchprisonscore.mitchcells.configs;

import com.massivecraft.massivecore.store.Entity;
import lombok.Getter;
import me.reklessmitch.mitchprisonscore.mitchcells.object.Cell;

import java.util.*;

public class CellConf extends Entity<CellConf> {

    public static CellConf i = new CellConf();
    public static CellConf get() { return i; }

    int maxCellSize = 10;
    @Getter Map<String, Cell> cells = new HashMap<>();

    public List<String> getCellNames(){
        return new ArrayList<>(cells.keySet());
    }

    public List<UUID> getAllMembers(){
        List<UUID> members = new ArrayList<>();
        for(Cell cell : cells.values()){
            members.addAll(cell.getMembers());
            members.add(cell.getOwner());
        }
        return members;
    }

    public Cell getCellByMember(UUID playerID){
        for(Cell cell : cells.values()){
            if(cell.getMembers().contains(playerID) || cell.getOwner().equals(playerID)){
                return cell;
            }
        }
        return null;
    }
}
