package me.reklessmitch.mitchprisonscore.mitchpickaxe.cmds.pickaxe;

import me.reklessmitch.mitchprisonscore.mitchpickaxe.cmds.PickaxeCommands;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import org.bukkit.entity.Player;

public class CmdAutoRankup extends PickaxeCommands {

    private static final CmdAutoRankup i = new CmdAutoRankup();
    public static CmdAutoRankup get() { return i; }

    public CmdAutoRankup() {
        this.addAliases("autorankup");
    }

    @Override
    public void perform() {
        Player player = (Player) sender;
        PPickaxe.get(player.getUniqueId()).toggleAutoRankup();
    }

}