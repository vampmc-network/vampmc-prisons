package me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.currency;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.CurrencyCommands;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayerColl;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.CurrencyUtils;

import java.util.Comparator;
import java.util.List;

public class CmdCurencyTop extends CurrencyCommands {

    public CmdCurencyTop(){
        this.addAliases("top");
        this.addParameter(TypeString.get(), "currency");
    }

    @Override
    public void perform() throws MassiveException {
        String currency = this.readArg();
        msg("§b§l+--- " + currency.toUpperCase() + " TOP ---+");
        List<ProfilePlayer> profiles = ProfilePlayerColl.get().getAll().stream()
                .limit(10)
                .sorted(Comparator.comparing(profile -> profile.getCurrency(currency).getAmount(), Comparator.reverseOrder()))
                .toList();
        for(int i = 0; i < profiles.size(); i++){
            ProfilePlayer profile = profiles.get(i);
            msg("§b" + (i + 1) + ". §f" + profile.getName() + " §7- §b" + CurrencyUtils.format(profile.getCurrency(currency).getAmount()));
        }
    }
}
