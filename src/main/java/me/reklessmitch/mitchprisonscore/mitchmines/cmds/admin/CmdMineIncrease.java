package me.reklessmitch.mitchprisonscore.mitchmines.cmds.admin;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeInteger;
import me.reklessmitch.mitchprisonscore.Perm;
import me.reklessmitch.mitchprisonscore.mitchmines.cmds.MineCommands;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.PlayerMine;
import org.bukkit.entity.Player;

public class CmdMineIncrease extends MineCommands {

    public CmdMineIncrease() {
        this.addAliases("upgrademine");
        this.addRequirements(RequirementIsPlayer.get());
        this.addRequirements(RequirementHasPerm.get(Perm.ADMIN));
        this.addParameter(1, TypeInteger.get(), "size");
    }

    @Override
    public void perform() throws MassiveException {
        int amount = this.readArg();
        Player player = (Player) sender;
        PlayerMine.get(player.getUniqueId()).upgradeSize(amount);
    }
}
