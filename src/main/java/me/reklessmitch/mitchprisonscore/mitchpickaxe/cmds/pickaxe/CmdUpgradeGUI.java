package me.reklessmitch.mitchprisonscore.mitchpickaxe.cmds.pickaxe;

import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.cmds.PickaxeCommands;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.gui.UpgradeGUI;

public class CmdUpgradeGUI extends PickaxeCommands {
    private static final CmdUpgradeGUI i = new CmdUpgradeGUI();
    public static CmdUpgradeGUI get() { return i; }

    public CmdUpgradeGUI() {
        this.addAliases("upgrade");
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() {
        new UpgradeGUI(me).open();
    }
}