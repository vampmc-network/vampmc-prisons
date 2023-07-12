package me.reklessmitch.mitchprisonscore.mitchboosters.engines;

import com.massivecraft.massivecore.Engine;
import me.reklessmitch.mitchprisonscore.MitchPrisonsCore;
import me.reklessmitch.mitchprisonscore.mitchboosters.configs.BoosterConf;
import me.reklessmitch.mitchprisonscore.mitchboosters.objects.Booster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class BoosterInteract extends Engine {

    private static BoosterInteract i = new BoosterInteract();
    public static BoosterInteract get() { return i; }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        ItemMeta meta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();
        if(!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
        if(meta.getPersistentDataContainer().has(MitchPrisonsCore.get().getKey(), PersistentDataType.STRING)){
            Booster booster = BoosterConf.get().getBoosters().get(
                    meta.getPersistentDataContainer().get(MitchPrisonsCore.get().getKey(), PersistentDataType.STRING));
            event.getPlayer().sendMessage("You have clicked a booster with multiplier " + booster.getMultiplier() + " and time " + booster.getTimeInSeconds() + " seconds");
        }
    }
}
