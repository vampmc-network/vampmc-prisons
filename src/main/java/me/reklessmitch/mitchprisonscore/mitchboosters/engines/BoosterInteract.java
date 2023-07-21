package me.reklessmitch.mitchprisonscore.mitchboosters.engines;

import com.massivecraft.massivecore.Engine;
import me.reklessmitch.mitchprisonscore.MitchPrisonsCore;
import me.reklessmitch.mitchprisonscore.mitchboosters.configs.BoosterPlayer;
import me.reklessmitch.mitchprisonscore.mitchboosters.objects.Booster;
import me.reklessmitch.mitchprisonscore.mitchboosters.utils.BoosterType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class BoosterInteract extends Engine {

    private static BoosterInteract i = new BoosterInteract();
    public static BoosterInteract get() { return i; }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        ItemMeta meta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();
        if(meta == null) return;
        if(!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
        if(meta.getPersistentDataContainer().has(MitchPrisonsCore.get().getKey(), PersistentDataType.STRING)){
            PersistentDataContainer pdc = meta.getPersistentDataContainer();
            BoosterType type = BoosterType.valueOf(pdc.get(MitchPrisonsCore.get().getKey(), PersistentDataType.STRING));
            double multiplier = pdc.get(MitchPrisonsCore.get().getKey(), PersistentDataType.DOUBLE);
            long time = pdc.get(MitchPrisonsCore.get().getKey(), PersistentDataType.LONG);
            event.getPlayer().sendMessage("You have clicked a booster with multiplier " + multiplier + " and time " + time + " seconds");
            BoosterPlayer.get(event.getPlayer().getUniqueId()).getBoosters().add(new Booster(type, multiplier, time));
        }
    }
}
