package me.reklessmitch.mitchprisonscore.mitchpets.placeholders;


import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PPlayer;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PetPlaceholders extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "mitchpets";
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
        if(params.equalsIgnoreCase("activepet")) {
            return PPlayer.get(player.getUniqueId()).getActivePet().name();
        }
        return "Not Active";
    }
}

