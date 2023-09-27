package me.reklessmitch.mitchprisonscore.mitchpets.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;

import me.reklessmitch.mitchprisonscore.mitchpets.entity.PetPlayer;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PetConf;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PetType;
import me.reklessmitch.mitchprisonscore.mitchpets.util.DisplayItem;
import me.reklessmitch.mitchprisonscore.utils.LangConf;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.UUID;

public class PetGUI extends ChestGui {

    private final PetPlayer petPlayer;

    public PetGUI(UUID pID){
        this.petPlayer = PetPlayer.get(pID);
        setInventory(Bukkit.createInventory(null, 18, LangConf.get().getPetGuiTitle()));
        setUpInventory();
        setAutoclosing(false);
        setSoundOpen(null);
        setSoundClose(null);
        add();
    }

    private void setUpInventory() {
        Map<PetType, DisplayItem> displayItems = PetConf.get().getPetDisplayItems();
        petPlayer.getPets().values().forEach(pet -> {
            int slot = displayItems.get(pet.getType()).getSlot();
            getInventory().setItem(slot, displayItems.get(pet.getType()).getGuiItem(pet.getLevel()));
            setAction(slot, event -> {
                if(petPlayer.getActivePet() == pet.getType()) {
                    event.getWhoClicked().sendMessage("You already have the " + pet.getType().name() + " pet selected.");
                    return true;
                }
                petPlayer.setActivePet(pet.getType());
                event.getWhoClicked().sendMessage("You have selected the " + pet.getType().name() + " pet." + " Level: " + pet.getLevel());
                setUpInventory();
                petPlayer.changed();
                return true;
            });
        });
    }

    public void open() {
        petPlayer.getPlayer().openInventory(getInventory());
    }
}
