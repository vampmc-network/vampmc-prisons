package me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.currency;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeLong;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.CurrencyCommands;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilesConf;
import org.bukkit.entity.Player;

public class CmdCurrencyPay extends CurrencyCommands {

    public CmdCurrencyPay(){
        this.addAliases("pay");
        this.addParameter(TypePlayer.get(), "player");
        this.addParameter(TypeString.get(), "currency");
        this.addParameter(TypeLong.get(), "amount");
    }

    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        ProfilePlayer receiver = ProfilePlayer.get(player.getUniqueId());
        ProfilePlayer sender = ProfilePlayer.get(me.getUniqueId());
        if(receiver == null){
            msg("<b>Player has not joined the server before");
            return;
        }
        if(player.getUniqueId() == me.getUniqueId()){
            msg("<b>You cannot pay yourself");
            return;
        }
        String currency = this.readArg();
        if(!ProfilesConf.get().getCurrencyList().contains(currency)){
            return;
        }
        long amount = this.readArg();
        if(amount <= 0){
            msg("<b>Amount must be greater than 0");
            return;
        }
        if(sender.getCurrency("token").getAmount() < amount){
            msg("<b>You do not have enough " + currency + "/s");
        }else{
            sender.getCurrency("token").take(amount);
            receiver.getCurrency("token").add(amount);
            sender.changed();
            receiver.changed();
            msg("<g>You have sent " + amount + " " + currency + "/s to " + player.getName());
            player.sendMessage("<g>You have received " + amount + " " + currency + "/s from " + me.getName());
        }
    }
}
