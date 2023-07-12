package me.reklessmitch.mitchprisonscore.mitchmines.engine;

import com.massivecraft.massivecore.Engine;
import com.sk89q.worldedit.math.BlockVector3;
import me.reklessmitch.mitchprisonscore.MitchPrisonsCore;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.PlayerMine;
import me.reklessmitch.mitchprisonscore.mitchmines.utils.BlockInPmineBrokeEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class MineEvents extends Engine {

    private static MineEvents i = new MineEvents();
    public static MineEvents get() { return i; }


    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(e.getPlayer().hasPlayedBefore()) return;
        PlayerMine playerMine = PlayerMine.get(e.getPlayer().getUniqueId());
        playerMine.createMine();
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e){
        if(!e.getPlayer().getWorld().getName().equals("privatemines")) return;
        e.setCancelled(true);
        Block block = e.getBlock();
        block.setType(Material.AIR);
        BlockVector3 blockVector3 = BlockVector3.at(block.getX(), block.getY(), block.getZ());
        PlayerMine playerMine = PlayerMine.get(e.getPlayer().getUniqueId());
        if(!playerMine.isInMine(blockVector3)) return;
        if(block.getType().equals(Material.ENDER_CHEST)){
            Bukkit.broadcastMessage("DO SUPPLY DROP DROPS");
        }
        BlockInPmineBrokeEvent event = new BlockInPmineBrokeEvent(e.getPlayer(), playerMine, e.getBlock());
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    @EventHandler(ignoreCancelled = true)
    public void onTeleportToMine(PlayerTeleportEvent e){
        if(!e.getTo().getWorld().getName().equals("privatemines")) return;
        PlayerMine playerMine = PlayerMine.get(e.getPlayer().getUniqueId());
        Bukkit.getScheduler().runTaskLater(MitchPrisonsCore.get(), () -> spoofWorldBorder(e.getPlayer(), playerMine.getMiddleLocation().toLocation(), 250), 40);
    }

    public void spoofWorldBorder(Player player, Location center, double size) {
        World world = center.getWorld();
        WorldBorder worldBorder = world.getWorldBorder();
        worldBorder.setCenter(center.getX(), center.getZ());
        worldBorder.setSize(size);
        player.setWorldBorder(worldBorder);
    }
}
