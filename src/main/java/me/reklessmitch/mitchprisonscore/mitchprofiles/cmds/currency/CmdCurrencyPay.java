package me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.currency;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.CurrencyCommands;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilesConf;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.CurrencyUtils;
import org.bukkit.entity.Player;

import java.math.BigInteger;

public class CmdCurrencyPay extends CurrencyCommands {

    private static CmdCurrencyPay i = new CmdCurrencyPay();
    public static CmdCurrencyPay get() { return i; }

    public CmdCurrencyPay(){
        this.addAliases("pay");
        this.addParameter(TypePlayer.get(), "player");
        this.addParameter(TypeString.get(), "currency");
        this.addParameter(TypeString.get(), "amount");
    }

    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        ProfilePlayer receiver = ProfilePlayer.get(player.getUniqueId());
        ProfilePlayer sender = ProfilePlayer.get(me.getUniqueId());
        if(receiver == null){
            msg("§cPlayer has not joined the server before");
            return;
        }
        if(player.getUniqueId() == me.getUniqueId()){
            msg("§cYou cannot pay yourself");
            return;
        }
        String currency = this.readArg();
        if(!ProfilesConf.get().getCurrencyList().contains(currency)){
            return;
        }

        String amount = this.readArg();
        BigInteger amountInt = CurrencyUtils.parse(amount);
        if(amountInt.longValue() == -1){
            msg("§cInvalid amount / character (k, m, b)");
            return;
        }
        if(amountInt.longValue() <= 0){
            msg("§cAmount must be greater than 0");
            return;
        }
        if (sender.getCurrency(currency).getAmount().compareTo(amountInt) < 0) {
            msg("§bYou do not have enough " + currency + "/s");
        }else{
            sender.getCurrency(currency).take(amountInt);
            receiver.getCurrency(currency).add(amountInt);
            sender.changed();
            receiver.changed();
            msg("§aYou have sent " + amount + " " + currency + "/s to " + player.getName());
            player.sendMessage("§aYou have received " + amount + " " + currency + "/s from " + me.getName());
        }
    }
}
