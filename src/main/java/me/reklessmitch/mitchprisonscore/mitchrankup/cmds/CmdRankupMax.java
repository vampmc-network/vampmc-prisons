package me.reklessmitch.mitchprisonscore.mitchrankup.cmds;

public class CmdRankupMax extends RankupCommands {

    private static final CmdRankupMax i = new CmdRankupMax();
    public static CmdRankupMax get() { return i; }

    public CmdRankupMax() {
        this.setAliases("rankupmax", "rmax");
        this.setDesc("Rankup Max");
    }

    @Override
    public void perform() {
        // @TODO Rankup multiple times using the formula in CmdRankup

    }
}
