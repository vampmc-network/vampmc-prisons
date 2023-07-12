package me.reklessmitch.mitchprisonscore.mitchmines.cmds.admin;

import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import me.reklessmitch.mitchprisonscore.mitchmines.cmds.MineCommands;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.PlayerMine;
import org.bukkit.entity.Player;

public class CmdMineIncrease extends MineCommands {


    private static final CmdMineIncrease i = new CmdMineIncrease();

    public static CmdMineIncrease get() {
        return i;
    }

    public CmdMineIncrease() {
        this.addAliases("upgrademine");
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() {
        Player player = (Player) sender;
        PlayerMine.get(player.getUniqueId()).upgradeSize();
    }
}
