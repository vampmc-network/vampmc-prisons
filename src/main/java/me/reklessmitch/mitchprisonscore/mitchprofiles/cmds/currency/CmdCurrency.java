package me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.currency;

import me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.CurrencyCommands;

public class CmdCurrency extends CurrencyCommands {

    private static CmdCurrency i = new CmdCurrency();
    public static CmdCurrency get() {return i;}

    public CmdCurrencyAdd cmdCurrencyAdd = new CmdCurrencyAdd();
    public CmdCurrencyPay cmdCurrencyPay = new CmdCurrencyPay();
    public CmdCurrencyRemove cmdCurrencyRemove = new CmdCurrencyRemove();
    public CmdCurrencySet cmdCurrencySet = new CmdCurrencySet();

    public CmdCurrency() {
        this.addAliases("currency");
        this.addChild(cmdCurrencyAdd);
        this.addChild(cmdCurrencyPay);
        this.addChild(cmdCurrencyRemove);
        this.addChild(cmdCurrencySet);
    }
}
