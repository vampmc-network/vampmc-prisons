package me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.currency;

import me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.CurrencyCommands;
import me.reklessmitch.mitchprisonscore.mitchprofiles.guis.StoreShop;
import org.bukkit.entity.Player;

public class CmdBuy extends CurrencyCommands {

    private static final CmdBuy i = new CmdBuy();
    public static CmdBuy get() { return i; }

    public CmdBuy() {
        this.setAliases("buy", "cshop", "shop");
        this.setDesc("Credit Shop");
    }

    @Override
    public void perform(){
        Player player = (Player) sender;
        new StoreShop(player).open();
    }
}
