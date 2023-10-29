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
        if(meta == null){
            return;
        }

        if(!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        if(pdc.isEmpty()) return;
        if(pdc.has(MitchPrisonsCore.get().getTypeKey(), PersistentDataType.STRING)
            && pdc.has(MitchPrisonsCore.get().getMultiKey(), PersistentDataType.DOUBLE)
            && pdc.has(MitchPrisonsCore.get().getDurationKey(), PersistentDataType.LONG)) {
            String id = pdc.get(MitchPrisonsCore.get().getTypeKey(), PersistentDataType.STRING);

            BoosterType type = BoosterType.valueOf(id);
            double multiplier = pdc.get(MitchPrisonsCore.get().getMultiKey(), PersistentDataType.DOUBLE);
            long time = pdc.get(MitchPrisonsCore.get().getDurationKey(), PersistentDataType.LONG);
            BoosterPlayer boosterPlayer = BoosterPlayer.get(event.getPlayer().getUniqueId());
            if(boosterPlayer.getBoosters().size() >= 35){
                event.getPlayer().sendMessage("§cYou can't have more than 35 boosters! (Combine them!)");
                return;
            }
            event.getPlayer().sendMessage("§aAdded booster with " + multiplier + "x to /boosters");
            boosterPlayer.getBoosters().add(new Booster(type, multiplier, time));
            event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
        }
    }
}
