package me.reklessmitch.mitchprisonscore.mitchrankup.cmds;

import me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.currency.CmdBuy;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.currency.MitchCurrency;
import me.reklessmitch.mitchprisonscore.mitchrankup.config.RankupConf;
import org.bukkit.entity.Player;

public class CmdRankup extends RankupCommands{

    private static final CmdRankup i = new CmdRankup();
    public static CmdRankup get() { return i; }

    CmdRankupGUI cmdRankupGUI = new CmdRankupGUI();
    CmdRankupMax cmdRankupMax = new CmdRankupMax();

    public CmdRankup() {
        this.setAliases("rankup", "levelup");
        this.addChild(cmdRankupGUI);
        this.addChild(cmdRankupMax);
    }

    @Override
    public void perform(){
        Player player = (Player) sender;
        ProfilePlayer pp = ProfilePlayer.get(player.getUniqueId());
        MitchCurrency money = pp.getCurrency("money");
        int cost = RankupConf.get().getCost(pp.getRank());
        if(money.getAmount() < cost){
            player.sendMessage("§cYou do not have enough money to rankup!");
            return;
        }
        money.take(cost);
        pp.setRank(pp.getRank() + 1);
        player.sendMessage("§aYou have ranked up to " + pp.getRank());
        pp.changed();
    }

}