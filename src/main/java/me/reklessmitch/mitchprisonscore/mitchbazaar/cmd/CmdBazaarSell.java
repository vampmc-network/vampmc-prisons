package me.reklessmitch.mitchprisonscore.mitchbazaar.cmd;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeLong;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import me.reklessmitch.mitchprisonscore.mitchbazaar.config.BazaarConf;
import me.reklessmitch.mitchprisonscore.mitchbazaar.object.ShopValue;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;

public class CmdBazaarSell extends BazaarCommands{

    public CmdBazaarSell(){
        this.addAliases("sell");
        this.addParameter(TypeString.get(), "currencyToSell");
        this.addParameter(TypeLong.get(), "amount");
        this.addParameter(TypeString.get(), "currencyToSellFor");
        this.addParameter(TypeLong.get(), "price");
    }

    @Override
    public void perform() throws MassiveException {
        String sell = this.readArg();
        long amount = this.readArg();
        String buy = this.readArg();
        long price = this.readArg();

        BazaarConf conf = BazaarConf.get();
        ProfilePlayer pp = ProfilePlayer.get(me.getUniqueId());
        if(pp.getCurrency(sell).getAmount() < amount){
            msg("§cYou do not have enough %s", sell);
            return;
        }
        conf.getSellPrices().get(sell).get(buy).add(new ShopValue(me.getUniqueId(), amount, price));
        conf.changed();
        msg("§aYou have added §e%s %s §ato the sell shop for §e%s %s/s", amount, sell, price, buy);
        pp.getCurrency(sell).take(amount);
        pp.changed();
    }
}
