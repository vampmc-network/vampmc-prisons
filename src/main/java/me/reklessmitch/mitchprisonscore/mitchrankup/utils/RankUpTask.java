package me.reklessmitch.mitchprisonscore.mitchrankup.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.currency.MitchCurrency;
import me.reklessmitch.mitchprisonscore.mitchrankup.config.RankupConf;
import me.reklessmitch.mitchprisonscore.utils.LangConf;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RankUpTask extends BukkitRunnable {
    private final Player player;
    private final ProfilePlayer pp;
    private final MitchCurrency money;
    private final RankupConf rankupConf;
    private int rank;

    public RankUpTask(Player player, ProfilePlayer pp, MitchCurrency money, RankupConf rankupConf) {
        this.player = player;
        this.pp = pp;
        this.money = money;
        this.rankupConf = rankupConf;
        this.rank = pp.getRank();
    }

    @Override
    public void run() {
        repeat();
    }

    private void repeat(){
        long cost = rankupConf.getCost(rank);
        if (money.getAmount() >= cost){
            money.take(cost);
            rank++;
            repeat();
        } else {
            pp.setRank(rank);
            pp.changed();
            player.sendMessage(PlaceholderAPI.setPlaceholders(player, LangConf.get().getRankUp()));
            super.cancel();
        }
    }
}