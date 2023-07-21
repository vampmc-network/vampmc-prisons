package me.reklessmitch.mitchprisonscore.mitchbackpack.engine;

import com.massivecraft.massivecore.Engine;
import me.reklessmitch.mitchprisonscore.mitchbackpack.config.BackpackPlayer;
import me.reklessmitch.mitchprisonscore.mitchbattlepass.events.BlocksMinedEvent;
import org.bukkit.event.EventHandler;

public class BlocksToBackpack extends Engine {

    private static BlocksToBackpack i = new BlocksToBackpack();
    public static BlocksToBackpack get() { return i; }

    @EventHandler(ignoreCancelled = true)
    public void blocksToAdd(BlocksMinedEvent e){
        BackpackPlayer.get(e.getPlayer().getUniqueId()).add(e.getBlocksBroken());
    }
}
