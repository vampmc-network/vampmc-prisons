package me.reklessmitch.mitchprisonscore.mitchpets.cmd;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeInteger;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PPlayer;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PetType;

public class CmdPetRemoveLevel extends PetCommand{

    public CmdPetRemoveLevel() {
        this.addAliases("removeLevel");
        this.addParameter(TypeString.get(), "petType");
        this.addParameter(TypeInteger.get(), "level");
    }

    @Override
    public void perform() throws MassiveException {
        String petType = this.readArg();
        int level = this.readArg();
        PPlayer pPlayer = PPlayer.get(me.getUniqueId());
        pPlayer.getPet(PetType.valueOf(petType)).removeLevel(level);
        pPlayer.changed();
    }
}