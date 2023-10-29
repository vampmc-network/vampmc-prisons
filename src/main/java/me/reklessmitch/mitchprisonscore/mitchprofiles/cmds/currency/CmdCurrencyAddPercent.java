package me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.currency;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeInteger;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.CurrencyCommands;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.currency.MitchCurrency;
import me.reklessmitch.mitchprisonscore.mitchrankup.config.RankupConf;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.math.BigInteger;

public class CmdCurrencyAddPercent extends CurrencyCommands {

    private static final CmdCurrencyAddPercent i = new CmdCurrencyAddPercent();
    public static CmdCurrencyAddPercent get() { return i; }


    public CmdCurrencyAddPercent() {
        this.addAliases("percent", "addp", "addpercent");
        this.addParameter(TypePlayer.get(), "player");
        this.addParameter(TypeInteger.get(), "percentage");
        this.setDesc("Add a percentage to a players balance for the next rankup");
    }

    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        int percent = this.readArg();
        ProfilePlayer pp = ProfilePlayer.get(player.getUniqueId());
        BigInteger cost = RankupConf.get().getCost(pp.getRank());
        MitchCurrency money = pp.getCurrency("money");

        BigInteger amountToAdd = cost.multiply(BigInteger.valueOf(percent)).divide(BigInteger.valueOf(100));

        player.sendMessage("§7You have been given §a" + percent + "% §7of §a" + cost + " §7of your next rankup §a" + amountToAdd + "§7.");

        money.add(amountToAdd);
        pp.changed();
    }
}
