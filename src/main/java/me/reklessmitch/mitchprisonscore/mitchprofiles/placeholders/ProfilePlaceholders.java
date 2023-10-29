package me.reklessmitch.mitchprisonscore.mitchprofiles.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class ProfilePlaceholders extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "mitchprofiles";
    }

    @Override
    public @NotNull String getAuthor() {
        return "ReklessMitch";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase("rank")){
            return String.valueOf(ProfilePlayer.get(player.getUniqueId()).getRank());
        }
        return "Invalid Placeholder";
    }

}