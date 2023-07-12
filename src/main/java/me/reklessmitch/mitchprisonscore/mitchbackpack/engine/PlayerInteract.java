package me.reklessmitch.mitchprisonscore.mitchbackpack.engine;

import com.massivecraft.massivecore.Engine;
import me.reklessmitch.mitchprisonscore.mitchbackpack.config.BackpackPlayer;
import me.reklessmitch.mitchprisonscore.mitchbackpack.gui.UpgradeBackpackGUI;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerInteract extends Engine {

    private static PlayerInteract i = new PlayerInteract();
    public static PlayerInteract get() { return i; }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent e){
        BackpackPlayer.get(e.getPlayer().getUniqueId()).set();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if(event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.DRAGON_EGG)){
                new UpgradeBackpackGUI(event.getPlayer()).open();
            }
        }
    }

    @EventHandler
    public void moveBackpackEvent(InventoryClickEvent event){
        if(event.getSlot() == 0 || event.getSlot() == 8){
            event.setCancelled(true);
        }
    }
}
