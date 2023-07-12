package me.reklessmitch.mitchprisonscore.mitchcells.cmds.cellcmds;

import me.reklessmitch.mitchprisonscore.mitchcells.cmds.CellCommands;

public class CellInvite extends CellCommands {

    private static final CellInvite i = new CellInvite();
    public static CellInvite get() { return i; }

    public CellInvite(){
        this.addAliases("invite");
    }

    @Override
    public void perform() {

    }

}
