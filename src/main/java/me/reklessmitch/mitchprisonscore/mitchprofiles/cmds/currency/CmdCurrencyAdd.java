package me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.currency;

import me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.CurrencyCommands;

public class CmdCurrencyAdd extends CurrencyCommands {

    private static CmdCurrencyAdd i = new CmdCurrencyAdd();
    public static CmdCurrencyAdd get() { return i; }

    public CmdCurrencyAddPercent cmdCurrencyAddPercent = new CmdCurrencyAddPercent();
    public CmdCurrencyAddAmount cmdCurrencyAddAmount = new CmdCurrencyAddAmount();

    public CmdCurrencyAdd() {
        this.addChild(cmdCurrencyAddPercent);
        this.addChild(cmdCurrencyAddAmount);
    }
}
