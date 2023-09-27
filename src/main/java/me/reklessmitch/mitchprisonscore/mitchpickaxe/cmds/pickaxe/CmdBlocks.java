package me.reklessmitch.mitchprisonscore.mitchpickaxe.cmds.pickaxe;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.cmds.PickaxeCommands;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import org.bukkit.entity.Player;

public class CmdBlocks extends PickaxeCommands {

    private static final CmdBlocks i = new CmdBlocks();
    public static CmdBlocks get() { return i; }

    public CmdBlocks() {
        this.addAliases("blocks");
        this.addParameter(TypePlayer.get(), "player");
    }

    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        if(player == null) player = me.getPlayer();
        assert player != null;
        PPickaxe pick = PPickaxe.get(player.getUniqueId());
        msg("§3§l" + player.getName() + "§3Block Stats" +
                "\n§b| §6Blocks: §e" + pick.getBlocksBroken() +
                "\n§b| §6Raw Blocks: §e" + pick.getRawBlocksBroken());
    }
}
