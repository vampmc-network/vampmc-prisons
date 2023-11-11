package me.reklessmitch.mitchprisonscore.mitchrankup.cmds;

import me.reklessmitch.mitchprisonscore.colls.ProfilePlayerColl;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;

import java.util.*;
import java.util.stream.Collectors;

public class CmdRankTop extends RankupCommands {

    private static final CmdRankTop i = new CmdRankTop();
    public static CmdRankTop get() {
        return i;
    }

    public CmdRankTop() {
        this.setAliases("ranktop", "toprank");
    }

    @Override
    public void perform() {
        List<ProfilePlayer> sortedPlayers = ProfilePlayerColl.get().getAll().stream()
                .sorted(Comparator.comparingInt(ProfilePlayer::getRank).reversed())
                .limit(10).toList();

        Map<String, Integer> topRankPlayers = new HashMap<>();
        sortedPlayers.forEach(player -> topRankPlayers.put(player.getName(), player.getRank()));
        msg("§eTop 10 Ranks:");
        for (Map.Entry<String, Integer> entry : topRankPlayers.entrySet()) {
            msg("§e" + entry.getKey() + "§7: §e" + entry.getValue());
        }
    }
}