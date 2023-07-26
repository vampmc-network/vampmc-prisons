package me.reklessmitch.mitchprisonscore.mitchprofiles.placeholders;

import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class CurrencyPlaceholders extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "mitchcurrency";
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
        return ProfilePlayer.get(player.getUniqueId()).getCurrencyList().stream()
                .filter(currency -> params.equalsIgnoreCase(currency.getName()))
                .map(currency -> currency.convertToFigure(currency.getAmount()))
                .findFirst()
                .orElse("Invalid Currency");
    }

}

