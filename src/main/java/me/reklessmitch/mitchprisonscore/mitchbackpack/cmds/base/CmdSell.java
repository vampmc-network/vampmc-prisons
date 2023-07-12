package me.reklessmitch.mitchprisonscore.mitchbackpack.cmds.base;

import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import me.reklessmitch.mitchprisonscore.mitchbackpack.cmds.BackpackCommands;
import me.reklessmitch.mitchprisonscore.mitchbackpack.config.BackpackPlayer;

public class CmdSell extends BackpackCommands {

    private static final CmdSell i = new CmdSell();
    public static CmdSell get() { return i; }

    public CmdSell() {
        this.addAliases("sell");
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() {
        BackpackPlayer.get(me.getUniqueId()).sell();
    }
}
