package me.reklessmitch.mitchprisonscore.mitchmines.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.MineConf;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.PlayerMine;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class MinePlaceholders extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "mitchmines";
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
        if(params.equalsIgnoreCase("rank")) {
            return "" + PlayerMine.get(player.getUniqueId()).getRank();
        }
        if(params.equalsIgnoreCase("blockstonextrankup")) {
            return "" + MineConf.get().getNextMineLevelBlockRequirement(PlayerMine.get(player.getUniqueId()).getRank());
        }
        if(params.equalsIgnoreCase("blocksmined")) {
            return "" + PPickaxe.get(player.getUniqueId()).getBlocksBroken();
        }
        if(params.equalsIgnoreCase("rawblocksmined")) {
            return "" + PPickaxe.get(player.getUniqueId()).getRawBlocksBroken();
        }
        if(params.equalsIgnoreCase("blocksleftinmine")) {
            return "" + (PlayerMine.get(player.getUniqueId()).getVolume() - PlayerMine.get(player.getUniqueId()).getVolumeMined());
        }
        return "N/A";
    }
}
