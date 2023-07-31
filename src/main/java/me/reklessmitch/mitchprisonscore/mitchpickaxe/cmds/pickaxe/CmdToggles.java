package me.reklessmitch.mitchprisonscore.mitchpickaxe.cmds.pickaxe;

import me.reklessmitch.mitchprisonscore.mitchpickaxe.cmds.PickaxeCommands;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.gui.TogglesGUI;

public class CmdToggles extends PickaxeCommands {

    private static final CmdToggles i = new CmdToggles();
    public static CmdToggles get() { return i; }

    public CmdToggles() {
        this.addAliases("toggles");
    }

    @Override
    public void perform() {
        new TogglesGUI(me, true).open();
    }

}
