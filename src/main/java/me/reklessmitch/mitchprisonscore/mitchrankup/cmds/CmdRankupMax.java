package me.reklessmitch.mitchprisonscore.mitchrankup.cmds;

import me.reklessmitch.mitchprisonscore.MitchPrisonsCore;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.currency.MitchCurrency;
import me.reklessmitch.mitchprisonscore.mitchrankup.config.RankupConf;
import me.reklessmitch.mitchprisonscore.mitchrankup.utils.RankUpTask;
import org.bukkit.entity.Player;

public class CmdRankupMax extends RankupCommands {

    private static final CmdRankupMax i = new CmdRankupMax();
    public static CmdRankupMax get() { return i; }

    public CmdRankupMax() {
        this.setAliases("rankupmax", "rmax");
        this.setDesc("Rankup Max");
    }

    @Override
    public void perform(){
        Player player = (Player) sender;
        ProfilePlayer pp = ProfilePlayer.get(player.getUniqueId());
        MitchCurrency money = pp.getCurrency("money");
        new RankUpTask(player, pp, money, RankupConf.get()).runTaskAsynchronously(MitchPrisonsCore.get());
    }

}
