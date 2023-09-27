package me.reklessmitch.mitchprisonscore.mitchcells.object;

import com.massivecraft.massivecore.util.IdUtil;
import lombok.Getter;
import me.reklessmitch.mitchprisonscore.mitchcells.configs.CellConf;
import org.bukkit.OfflinePlayer;

import java.util.*;

@Getter
public class Cell {

    private String name;
    private UUID owner;
    private Set<UUID> officers;
    private Set<UUID> members;
    private long beacons;
    private Set<UUID> invites;

    public Cell(String name, UUID owner){
        this.name = name;
        this.owner = owner;
        this.beacons = 0;
        this.members = new HashSet<>();
        this.invites = new HashSet<>();
        this.officers = new HashSet<>();
    }

    public void addBeacons(long amount) {
        beacons += amount;
        getAllMembers().forEach(uuid -> {
            OfflinePlayer player = IdUtil.getOfflinePlayer(uuid);
            if(player.isOnline() && player.getPlayer() != null){
                player.getPlayer().sendMessage("§6" + amount + " §7beacons deposited into cell from §6" + player.getName());
            }
        });
    }

    public void disband() {
        getAllMembers().forEach(uuid -> {
            OfflinePlayer player = IdUtil.getOfflinePlayer(uuid);
            if(player.isOnline() && player.getPlayer() != null){
                player.getPlayer().sendMessage("§7Your cell has been disbanded");
            }
        });
        CellConf.get().getCells().remove(name);
    }

    public Set<UUID> getAllMembers(){
        Set<UUID> m = new HashSet<>();
        m.addAll(officers);
        m.addAll(members);
        m.add(owner);
        return m;
    }

    public Set<UUID> getAllHigherUps(){
        Set<UUID> m = new HashSet<>(officers);
        m.add(owner);
        return m;
    }

    public void removePlayer(UUID uniqueId) {
        members.remove(uniqueId);
        officers.remove(uniqueId);
        invites.remove(uniqueId);
    }
}
