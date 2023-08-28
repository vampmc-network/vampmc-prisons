package me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.currency;


import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import me.reklessmitch.mitchprisonscore.Perm;
import me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.CurrencyCommands;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.CurrencyUtils;
import org.bukkit.entity.Player;

public class CmdCurrencySet extends CurrencyCommands {

    public CmdCurrencySet() {
        this.addAliases("set");
        this.setDesc("Set currency of a player");
        this.addParameter(TypePlayer.get(), "player");
        this.addParameter(TypeString.get(), "currency");
        this.addParameter(TypeString.get(), "amount");
        this.addRequirements(RequirementHasPerm.get(Perm.ADMIN));
    }

    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        String currency = this.readArg();
        String amount = this.readArg();
        long amountInt = CurrencyUtils.parse(amount);
        if(amountInt == -1){
            msg("§cInvalid amount / character (k, m, b)");
            return;
        }
        ProfilePlayer pp = ProfilePlayer.get(player.getUniqueId());
        pp.getCurrency(currency).set(amountInt);
        pp.changed();
        this.msg("§aYou have set §c%s §ato §c%s's §cbalance", amountInt, player.getName());
    }
}
