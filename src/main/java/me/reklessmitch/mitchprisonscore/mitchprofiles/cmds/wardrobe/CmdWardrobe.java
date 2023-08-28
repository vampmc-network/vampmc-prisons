package me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.wardrobe;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.CurrencyCommands;
import me.reklessmitch.mitchprisonscore.mitchprofiles.holograms.PlayerWardrobe;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CmdWardrobe extends CurrencyCommands {

    private static CmdWardrobe i = new CmdWardrobe();
    public static CmdWardrobe get() { return i; }

    public CmdWardrobe() {
        this.addAliases("wardrobe");
        this.addParameter(TypeString.get(), "piece");
    }

    @Override
    public void perform() throws MassiveException {
        Player player = (Player) this.sender;
        PlayerWardrobe wardrobe = PlayerWardrobe.get(player);
        ItemStack item = player.getInventory().getItemInMainHand();
        String piece = this.readArg();
        switch (piece.toLowerCase()) {
            case "hat" -> wardrobe.setHelmet(item);
            case "chest" -> wardrobe.setChestplate(item);
            case "legs" -> wardrobe.setLeggings(item);
            case "boots" -> wardrobe.setBoots(item);
            case "offhand" -> wardrobe.setOffhand(item);
            default -> this.msg("<b>Invalid piece of clothing");
        }
    }


}
