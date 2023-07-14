package me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.currency;

import me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.CurrencyCommands;

public class CmdCurrency extends CurrencyCommands {

    public CmdCurrencyAdd cmdCurrencyAdd = new CmdCurrencyAdd();
    public CmdCurrencyRemove cmdCurrencyRemove = new CmdCurrencyRemove();
    public CmdCurrencySet cmdCurrencySet = new CmdCurrencySet();

    public CmdCurrency() {
        this.addChild(cmdCurrencyAdd);
        this.addChild(cmdCurrencyRemove);
        this.addChild(cmdCurrencySet);
    }
}
