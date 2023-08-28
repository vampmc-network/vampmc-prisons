package me.reklessmitch.mitchprisonscore.mitchmines.engine;

import com.massivecraft.massivecore.Engine;
import com.sk89q.worldedit.math.BlockVector3;
import me.reklessmitch.mitchprisonscore.MitchPrisonsCore;
import me.reklessmitch.mitchprisonscore.mitchbattlepass.events.BlocksMinedEvent;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.MineConf;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.PlayerMine;
import me.reklessmitch.mitchprisonscore.mitchmines.utils.BlockInPmineBrokeEvent;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class MineEvents extends Engine {

    private static MineEvents i = new MineEvents();
    public static MineEvents get() { return i; }


    @EventHandler(ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent e){
        if(e.getPlayer().hasPlayedBefore()) {
            return;
        }
        PlayerMine playerMine = PlayerMine.get(e.getPlayer().getUniqueId());
        playerMine.generateSchematic();
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e){
        if(!e.getPlayer().getWorld().getName().equals("privatemines")) return;
        e.setCancelled(true);
        Block block = e.getBlock();
        BlockVector3 blockVector3 = BlockVector3.at(block.getX(), block.getY(), block.getZ());
        PlayerMine playerMine = PlayerMine.get(e.getPlayer().getUniqueId());
        if(!playerMine.isInMine(blockVector3)){
            e.getPlayer().sendMessage("§cYou can only break blocks in your mine");
            return;
        }
        block.setType(Material.AIR);
        if(block.getType().equals(Material.ENDER_CHEST)){
            Bukkit.broadcastMessage("DO SUPPLY DROP DROPS");
        }
        BlockInPmineBrokeEvent event = new BlockInPmineBrokeEvent(e.getPlayer(), playerMine, e.getBlock());
        Bukkit.getServer().getPluginManager().callEvent(event);
    }


    @EventHandler(ignoreCancelled = true)
    public void onTeleportToMine(PlayerChangedWorldEvent e){
        if(!e.getPlayer().getWorld().getName().equals("privatemines")) return;
        PlayerMine playerMine = PlayerMine.get(e.getPlayer().getUniqueId());
        Bukkit.getScheduler().runTaskLater(MitchPrisonsCore.get(), () ->
                spoofWorldBorder(e.getPlayer(), playerMine.getMiddleLocation().toLocation(), 124), 40);
    }

    public void spoofWorldBorder(Player player, Location center, double size) {
        MitchPrisonsCore.get().getWorldBorderApi().setBorder(player, size, center);
    }

    @EventHandler(ignoreCancelled = true)
    public void mineUpgradeCheck(BlocksMinedEvent e){
        PlayerMine playerMine = PlayerMine.get(e.getPlayer().getUniqueId());
        long blocksMined = PPickaxe.get(e.getPlayer().getUniqueId()).getBlocksBroken();
        if(playerMine.getSize() >= MineConf.get().getMaxMineSize()) return;
        if(MineConf.get().getNextMineLevelBlockRequirement(playerMine.getSize()) <= blocksMined){
            playerMine.upgradeSize(1, false);
        }

    }
}
