package me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.currency;

import com.massivecraft.massivecore.MassiveException;
import me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.CurrencyCommands;
import me.reklessmitch.mitchprisonscore.mitchprofiles.guis.StoreCategories;
import org.bukkit.entity.Player;

public class CmdCurrencyShop extends CurrencyCommands {

    private static CmdCurrencyShop i = new CmdCurrencyShop();
    public static CmdCurrencyShop get() { return i; }

    public CmdCurrencyShop() {
        this.addAliases("store", "buy", "shop");
    }

    @Override
    public void perform() throws MassiveException {
        new StoreCategories().open((Player) sender);
    }
}
