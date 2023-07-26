package me.reklessmitch.mitchprisonscore.mitchboosters.cmds.booster;

import me.reklessmitch.mitchprisonscore.mitchboosters.cmds.BoosterCommands;
import me.reklessmitch.mitchprisonscore.mitchboosters.guis.BoosterGUI;
import org.bukkit.entity.Player;

public class CmdBooster extends BoosterCommands {

    private static final CmdBooster i = new CmdBooster();
    public static CmdBooster get() { return i; }

    private final CmdGive cmdGive = new CmdGive();
    private final CmdBoosterGUI cmdGUI = new CmdBoosterGUI();

    public CmdBooster(){
        this.addAliases("booster", "boost", "boosters");
        this.addChild(this.cmdGive);
        this.addChild(this.cmdGUI);
    }

    @Override
    public void perform() {
        Player player = (Player) sender;
        new BoosterGUI(player.getUniqueId()).open(player);
    }

}
