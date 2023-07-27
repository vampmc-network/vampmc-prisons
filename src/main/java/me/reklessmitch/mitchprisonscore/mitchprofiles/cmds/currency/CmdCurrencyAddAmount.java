package me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.currency;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.primitive.TypeLong;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import me.reklessmitch.mitchprisonscore.Perm;
import me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.CurrencyCommands;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.currency.MitchCurrency;
import org.bukkit.entity.Player;

public class CmdCurrencyAddAmount extends CurrencyCommands {

    private static CmdCurrencyAddAmount i = new CmdCurrencyAddAmount();
    public static CmdCurrencyAddAmount get() { return i; }

    public CmdCurrencyAddAmount() {
        this.addAliases("addc", "addcurrency", "addamount");
        this.setDesc("Add currency to a player");
        this.addParameter(TypePlayer.get(), "player");
        this.addParameter(TypeString.get(), "currency");
        this.addParameter(TypeLong.get(), "amount");
        this.addRequirements(RequirementHasPerm.get(Perm.ADMIN));
    }

    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        String currency = this.readArg();
        long amount = this.readArg();
        MitchCurrency c = ProfilePlayer.get(player.getUniqueId()).getCurrency(currency);
        c.add(amount);
        this.msg("<g>You have added <h>%s <g>to <h>%s's <g>balance", amount, player.getName());
    }
}
