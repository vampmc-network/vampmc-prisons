package me.reklessmitch.mitchprisonscore.mitchbazaar.cmd;

import me.reklessmitch.mitchprisonscore.mitchbazaar.guis.CurrencyGUI;

public class CmdBazaar extends BazaarCommands{

    private static final CmdBazaar i = new CmdBazaar();
    public static CmdBazaar get() { return i; }

    public CmdBazaar(){
        this.addAliases("bazaar");
    }

    @Override
    public void perform() {
        new CurrencyGUI(me).open();
    }
}
