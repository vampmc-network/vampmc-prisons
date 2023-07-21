package me.reklessmitch.mitchprisonscore.mitchboosters.cmds.booster;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.primitive.TypeDouble;
import com.massivecraft.massivecore.command.type.primitive.TypeLong;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import me.reklessmitch.mitchprisonscore.Perm;
import me.reklessmitch.mitchprisonscore.mitchboosters.cmds.BoosterCommands;
import me.reklessmitch.mitchprisonscore.mitchboosters.objects.Booster;
import me.reklessmitch.mitchprisonscore.mitchboosters.utils.BoosterType;
import org.bukkit.entity.Player;


public class CmdGive extends BoosterCommands {

    public CmdGive() {
        this.addAliases("give");
        this.addParameter(TypePlayer.get(), "player", "ReklessMitch");
        this.addParameter(TypeString.get(), "type", "token/money/beacon");
        this.addParameter(TypeDouble.get(), "multiplier", "2");
        this.addParameter(TypeLong.get(), "duration", "60");
        this.addRequirements(RequirementHasPerm.get(Perm.ADMIN));
    }

    @Override
    public void perform(){
        try {
            Player player = this.readArg();
            BoosterType type = BoosterType.valueOf(this.readArg().toString().toUpperCase());
            double multiplier = this.readArg();
            long duration = this.readArg();
            Booster booster = new Booster(type, multiplier, duration);
            player.getInventory().addItem(booster.getBoosterItem());
        } catch (MassiveException e) {
            me.sendMessage("Invalid arguments");
        }
    }
}
