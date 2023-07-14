package me.reklessmitch.mitchprisonscore.mitchmines.cmds.def;

import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.sk89q.worldedit.math.BlockVector3;
import me.reklessmitch.mitchprisonscore.mitchmines.cmds.MineCommands;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.PlayerMine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CmdMineGO extends MineCommands {

    private static final CmdMineGO i = new CmdMineGO();
    public static CmdMineGO get() { return i; }

    public CmdMineGO(){
        this.addAliases("go");
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() {
        PlayerMine.get(me.getUniqueId()).teleport();
    }
}
