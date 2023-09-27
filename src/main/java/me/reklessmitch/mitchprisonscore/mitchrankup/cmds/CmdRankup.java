package me.reklessmitch.mitchprisonscore.mitchrankup.cmds;

import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.currency.MitchCurrency;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.CurrencyUtils;
import me.reklessmitch.mitchprisonscore.mitchrankup.config.RankupConf;
import org.bukkit.entity.Player;

public class CmdRankup extends RankupCommands{

    private static final CmdRankup i = new CmdRankup();
    public static CmdRankup get() { return i; }

    public CmdRankup() {
        this.setAliases("rankup", "levelup");
    }

    @Override
    public void perform(){
        Player player = (Player) sender;
        ProfilePlayer pp = ProfilePlayer.get(player.getUniqueId());
        MitchCurrency money = pp.getCurrency("money");
        long cost = RankupConf.get().getCost(pp.getRank());
        if(money.getAmount() < cost){
            msg("§cYou do not have enough money to rankup!" + "§e You need " +
                    CurrencyUtils.format(cost - money.getAmount()) + " more money to rankup.");
            return;
        }
        money.take(cost);
        pp.setRank(pp.getRank() + 1);
        msg("§aYou have ranked up to " + pp.getRank());
        pp.changed();
    }

}
