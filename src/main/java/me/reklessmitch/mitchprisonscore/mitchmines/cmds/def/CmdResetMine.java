package me.reklessmitch.mitchprisonscore.mitchmines.cmds.def;

import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import me.reklessmitch.mitchprisonscore.mitchmines.cmds.MineCommands;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.PlayerMine;
import org.bukkit.entity.Player;

public class CmdResetMine extends MineCommands {

    private static final CmdResetMine i = new CmdResetMine();
    public static CmdResetMine get() { return i; }

    public CmdResetMine(){
        this.addAliases("reset");
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() {
        Player player = (Player) sender;
        PlayerMine.get(player.getUniqueId()).reset();
    }
}
