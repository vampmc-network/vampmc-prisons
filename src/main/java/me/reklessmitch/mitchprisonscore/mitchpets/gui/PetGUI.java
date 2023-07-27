package me.reklessmitch.mitchprisonscore.mitchpets.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;

import me.reklessmitch.mitchprisonscore.mitchpets.entity.PPlayer;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.Pet;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PetConf;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PetType;
import me.reklessmitch.mitchprisonscore.mitchpets.util.DisplayItem;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.massivecraft.massivecore.util.IdUtil.getPlayer;

public class PetGUI extends ChestGui {

    private final PPlayer pPlayer;

    public PetGUI(UUID pID){
        this.pPlayer = PPlayer.get(pID);
        setInventory(Bukkit.createInventory(null, 18, "Pets"));
        setUpInventory();
        setAutoclosing(false);
        setSoundOpen(null);
        setSoundClose(null);
        add();
    }

    private void setUpInventory() {
        Map<PetType, DisplayItem> displayItems =  PetConf.get().getPetDisplayItems();
        pPlayer.getPets().values().forEach(pet -> {
            int slot = displayItems.get(pet.getType()).getSlot();
            getInventory().setItem(slot, displayItems.get(pet.getType()).getGuiItem(pet.getLevel()));
            setAction(slot, event -> {
                if(pPlayer.getActivePet() == pet.getType()) {
                    event.getWhoClicked().sendMessage("You already have the " + pet.getType().name() + " pet selected.");
                    return true;
                }
                pPlayer.setActivePet(pet.getType());
                event.getWhoClicked().sendMessage("You have selected the " + pet.getType().name() + " pet." + " Level: " + pet.getLevel());
                setUpInventory();
                pPlayer.changed();
                return true;
            });
        });
    }

    public void open() {
        getPlayer(pPlayer.getPlayer()).openInventory(getInventory());
    }
}
