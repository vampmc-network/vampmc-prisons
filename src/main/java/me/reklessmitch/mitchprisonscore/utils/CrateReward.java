
package me.reklessmitch.mitchprisonscore.utils;

import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class CrateReward {

    @Getter double chance;
    List<String> commands;
    String message;

    public CrateReward(double chance, List<String> commands, String message) {
        this.chance = chance;
        this.commands = commands;
        this.message = message;
    }

    public List<String> getCommands(Player player) {
        commands.forEach(command -> PlaceholderAPI.setPlaceholders(player, command));
        return commands;
    }

    public String getMessage(Player player) {
        message = PlaceholderAPI.setPlaceholders(player, ChatColor.translateAlternateColorCodes('&', message));
        return message;
    }


}
