package me.reklessmitch.mitchprisonscore.mitchrankup.cmds;

import me.reklessmitch.mitchprisonscore.mitchrankup.gui.RankupRewardsGUI;
import org.bukkit.entity.Player;

public class CmdRankupGUI extends RankupCommands{

    private static final CmdRankup i = new CmdRankup();
    public static CmdRankup get() { return i; }

    public CmdRankupGUI() {
        this.setAliases("rankupgui", "rgui");
        this.setDesc("Rankup GUI");
    }

    @Override
    public void perform(){
        Player player = (Player) sender;
        new RankupRewardsGUI(player).open();
    }

}
