package me.reklessmitch.mitchprisonscore.mitchmines.cmds.def;

import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.sk89q.worldedit.math.BlockVector3;
import me.reklessmitch.mitchprisonscore.mitchmines.cmds.MineCommands;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.PlayerMine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CmdMine extends MineCommands {

    private static final CmdMine i = new CmdMine();
    public static CmdMine get() { return i; }

    public CmdMine(){
        this.addAliases("mine");
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() {
        Player player = (Player) sender;
        BlockVector3 vector3 = PlayerMine.get(player.getUniqueId()).getSpawnPoint().toBlockVector3();
        player.teleport(new Location(Bukkit.getWorld("privatemines"), vector3.getX(), vector3.getY(), vector3.getZ()));
    }
}
