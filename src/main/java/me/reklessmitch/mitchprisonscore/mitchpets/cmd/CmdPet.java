package me.reklessmitch.mitchprisonscore.mitchpets.cmd;

import com.massivecraft.massivecore.MassiveException;
import me.reklessmitch.mitchprisonscore.mitchpets.gui.PetGUI;
import org.bukkit.entity.Player;

public class CmdPet extends PetCommand{

    private static final CmdPet i = new CmdPet();
    public static CmdPet get() { return i; }

    private final CmdPetAddLevel cmdPetAddLevel = new CmdPetAddLevel();
    private final CmdPetRemoveLevel cmdPetRemoveLevel = new CmdPetRemoveLevel();
    private final CmdPetSetLevel cmdPetSetLevel = new CmdPetSetLevel();
    private final CmdPetGUI petGUI = new CmdPetGUI();

    public CmdPet(){
        this.addAliases("pet", "pets");
        this.addChild(this.cmdPetAddLevel);
        this.addChild(this.cmdPetRemoveLevel);
        this.addChild(this.cmdPetSetLevel);
        this.addChild(this.petGUI);
    }

    @Override
    public void perform() throws MassiveException {
        Player player = (Player) sender;
        new PetGUI(player.getUniqueId()).open();
    }
}
