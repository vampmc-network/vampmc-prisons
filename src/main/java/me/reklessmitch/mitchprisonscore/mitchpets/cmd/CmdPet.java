package me.reklessmitch.mitchprisonscore.mitchpets.cmd;

public class CmdPet extends PetCommand{

    private static final CmdPet i = new CmdPet();
    public static CmdPet get() { return i; }

    private final CmdPetAddLevel cmdPetAddLevel = new CmdPetAddLevel();
    private final CmdPetRemoveLevel cmdPetRemoveLevel = new CmdPetRemoveLevel();
    private final CmdPetSetLevel cmdPetSetLevel = new CmdPetSetLevel();
    private final CmdPetGUI petGUI = new CmdPetGUI();

    public CmdPet(){
        this.addAliases("pet");
        this.addChild(this.cmdPetAddLevel);
        this.addChild(this.cmdPetRemoveLevel);
        this.addChild(this.cmdPetSetLevel);
        this.addChild(this.petGUI);
    }
}
