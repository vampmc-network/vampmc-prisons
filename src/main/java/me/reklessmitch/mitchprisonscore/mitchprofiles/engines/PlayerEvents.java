package me.reklessmitch.mitchprisonscore.mitchprofiles.engines;

import com.massivecraft.massivecore.Engine;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEvents extends Engine {
    private static PlayerEvents i = new PlayerEvents();
    public static PlayerEvents get() { return i; }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent e){
        ProfilePlayer profile = ProfilePlayer.get(e.getPlayer());
        if(!profile.getJoinMessage().equals("")){
            Bukkit.broadcastMessage(profile.getJoinMessage().replace("%player%", e.getPlayer().getName()));
        }
        // Do friend stuff here
    }

}
