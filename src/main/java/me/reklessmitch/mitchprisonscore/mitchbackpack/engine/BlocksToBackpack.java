package me.reklessmitch.mitchprisonscore.mitchbackpack.engine;

import com.massivecraft.massivecore.Engine;
import me.reklessmitch.mitchprisonscore.mitchbackpack.config.BackpackPlayer;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.events.BlockToBackpackEvent;
import org.bukkit.event.EventHandler;

public class BlocksToBackpack extends Engine {

    private static BlocksToBackpack i = new BlocksToBackpack();
    public static BlocksToBackpack get() { return i; }

    @EventHandler(ignoreCancelled = true)
    public void blocksToAdd(BlockToBackpackEvent e){
        BackpackPlayer.get(e.getPlayer().getUniqueId()).add(e.getAmount());
    }
}
