package me.reklessmitch.mitchprisonscore.mitchbackpack.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.reklessmitch.mitchprisonscore.mitchbackpack.config.BackpackPlayer;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class BackpackPlaceholders extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "mitchbackpacks";
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
        if(params.equalsIgnoreCase("backpack_size")){
            return BackpackPlayer.get(player.getUniqueId()).getCurrentLoad() + "";
        }
        if(params.equalsIgnoreCase("backpack_max_size")){
            return BackpackPlayer.get(player.getUniqueId()).getCapacity() + "";
        }

        return "Invalid Placeholder";
    }

}