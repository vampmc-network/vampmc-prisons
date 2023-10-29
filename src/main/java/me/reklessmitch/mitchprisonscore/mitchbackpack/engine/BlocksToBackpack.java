package me.reklessmitch.mitchprisonscore.mitchbackpack.engine;

import com.massivecraft.massivecore.Engine;
import me.reklessmitch.mitchprisonscore.mitchbackpack.config.BackpackPlayer;
import me.reklessmitch.mitchprisonscore.mitchbattlepass.events.BlocksMinedEvent;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.utils.EnchantType;
import org.bukkit.event.EventHandler;

import java.math.BigInteger;

public class BlocksToBackpack extends Engine {

    private static BlocksToBackpack i = new BlocksToBackpack();
    public static BlocksToBackpack get() { return i; }

    @EventHandler(ignoreCancelled = true)
    public void blocksToAdd(BlocksMinedEvent e){
        long startAmount = e.getBlocksBroken();
        PPickaxe ppickaxe = PPickaxe.get(e.getPlayer().getUniqueId());
        int fortuneLevel = ppickaxe.getEnchants().get(EnchantType.FORTUNE);
        double fortuneMulti = (double) fortuneLevel / 1000;
        if(fortuneLevel > 0) {
            startAmount *= 1 + fortuneMulti;
        }
        BackpackPlayer.get(e.getPlayer().getUniqueId()).add(BigInteger.valueOf(startAmount));
    }
}
