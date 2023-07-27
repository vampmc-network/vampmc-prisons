package me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.currency;

import me.clip.placeholderapi.PlaceholderAPI;
import me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.CurrencyCommands;
import org.bukkit.entity.Player;


public class CmdBal extends CurrencyCommands {

    private static final CmdBal i = new CmdBal();
    public static CmdBal get() { return i; }

    public CmdBal(){
        this.addAliases("bal");
    }

    @Override
    public void perform() {
        Player player = (Player) sender;
        msg("§b§l+--- BALANCE ---+");
        msg(PlaceholderAPI.setPlaceholders(player, "§3| §a %mitchcurrency_token% Tokens"));
        msg(PlaceholderAPI.setPlaceholders(player, "§3| §b %mitchcurrency_money% Money"));
        msg(PlaceholderAPI.setPlaceholders(player,"§3| §e %mitchcurrency_beacon% Beacons"));
        msg(PlaceholderAPI.setPlaceholders(player,"§3| §d %mitchcurrency_credits% Credits"));
    }
}
