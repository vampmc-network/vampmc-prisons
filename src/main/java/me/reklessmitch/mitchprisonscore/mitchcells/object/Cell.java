package me.reklessmitch.mitchprisonscore.mitchcells.object;

import com.massivecraft.massivecore.util.IdUtil;
import lombok.Getter;
import me.reklessmitch.mitchprisonscore.mitchcells.configs.CellConf;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.util.*;

@Getter
public class Cell {

    private String name;
    private UUID owner;
    private Set<UUID> officers;
    private Set<UUID> members;
    private BigInteger beacons;
    private Set<UUID> invites;

    public Cell(String name, UUID owner){
        this.name = name;
        this.owner = owner;
        this.beacons = BigInteger.ZERO;
        this.members = new HashSet<>();
        this.invites = new HashSet<>();
        this.officers = new HashSet<>();
    }

    public void addBeacons(BigInteger amount) {
        beacons = beacons.add(amount);
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
        CellConf cellConf = CellConf.get();
        cellConf.getCells().remove(name);
        cellConf.changed();

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

    public void removePlayer(UUID uniqueId, Player remover) {
        members.remove(uniqueId);
        officers.remove(uniqueId);
        invites.remove(uniqueId);
        OfflinePlayer player = IdUtil.getOfflinePlayer(uniqueId);
        remover.sendMessage("§aYou have kicked §c" + player.getName() + "§a from your cell");
        if(player.isOnline() && player.getPlayer() != null){
            player.getPlayer().sendMessage("§cYou have been kicked from your cell");
        }
    }

    public List<UUID> getAllMembersBelowUser(UUID uniqueId) {
        List<UUID> m = new ArrayList<>(members);
        if(owner.equals(uniqueId)) {
            m.addAll(officers);
        }
        return m;
    }
}
