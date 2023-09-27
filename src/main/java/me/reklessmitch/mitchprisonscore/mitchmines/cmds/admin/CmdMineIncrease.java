package me.reklessmitch.mitchprisonscore.mitchmines.cmds.admin;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeInteger;
import me.reklessmitch.mitchprisonscore.Perm;
import me.reklessmitch.mitchprisonscore.mitchmines.cmds.MineCommands;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.MineConf;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.PlayerMine;
import org.bukkit.entity.Player;

public class CmdMineIncrease extends MineCommands {

    public CmdMineIncrease() {
        this.addAliases("setsize");
        this.addRequirements(RequirementIsPlayer.get());
        this.addRequirements(RequirementHasPerm.get(Perm.ADMIN));
        this.addParameter(1, TypeInteger.get(), "size");
    }

    @Override
    public void perform() throws MassiveException {
        int amount = this.readArg();
        Player player = (Player) sender;
        PlayerMine playerMine = PlayerMine.get(player.getUniqueId());
        int maxMineSize = MineConf.get().getMaxMineSize();
        int newSize = playerMine.getSize() + amount;
        if(newSize > maxMineSize){
            msg("§aThis would exceed max mine size of " + maxMineSize + "setting to max mine size");
            playerMine.upgradeSize(maxMineSize, true);
            return;
        }
        playerMine.upgradeSize(newSize, true);
        msg("§aYou have set your mine size to " + newSize);
    }
}
