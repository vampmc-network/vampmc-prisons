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
        long cost = RankupConf.get().getCost(pp.getRank());
        MitchCurrency money = pp.getCurrency("money");
        Bukkit.broadcastMessage("§a" + player.getName() + " §7has been given §a" + percent + "% §7of §a" + cost + " §7for their next rankup");
        money.add(cost * (percent / 100));
        pp.changed();
    }


}
