package me.reklessmitch.mitchprisonscore.mitchpets.cmd;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeInteger;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PPlayer;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PetType;
import org.bukkit.entity.Player;

public class CmdPetSetLevel extends PetCommand{

    public CmdPetSetLevel() {
        this.addAliases("setLevel");
        this.addParameter(TypePlayer.get(), "player");
        this.addParameter(TypeString.get(), "petType");
        this.addParameter(TypeInteger.get(), "level");
    }

    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        String petType = this.readArg();
        int level = this.readArg();
        PPlayer pPlayer = PPlayer.get(player.getUniqueId());
        pPlayer.getPet(PetType.valueOf(petType.toUpperCase())).setLevel(level);
        pPlayer.changed();
    }
}
