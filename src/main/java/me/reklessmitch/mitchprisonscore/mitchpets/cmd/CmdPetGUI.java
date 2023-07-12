package me.reklessmitch.mitchprisonscore.mitchpets.cmd;

import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import me.reklessmitch.mitchprisonscore.mitchpets.gui.PetGUI;
import org.bukkit.entity.Player;

public class CmdPetGUI extends PetCommand{

    public CmdPetGUI() {
        this.addAliases("gui");
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() {
        Player player = (Player) sender;
        new PetGUI(player.getUniqueId()).open();
    }
}
