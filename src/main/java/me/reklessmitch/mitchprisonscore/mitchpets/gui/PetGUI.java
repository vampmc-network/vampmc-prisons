package me.reklessmitch.mitchprisonscore.mitchpets.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;

import me.reklessmitch.mitchprisonscore.colls.PPlayerColl;
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

    private final PPlayer player;
    private final Map<Integer, Pet> pets = new HashMap<>();

    public PetGUI(UUID pID){
        this.player = PPlayerColl.get().get(pID);
        setInventory(Bukkit.createInventory(null, 18, "Pets"));
        player.getPets().forEach(pet -> pets.put(pets.size(), pet));
        setUpInventory();
        setAutoclosing(false);
        setSoundOpen(null);
        setSoundClose(null);
        add();
    }

    private void setUpInventory() {
        Map<PetType, DisplayItem> display = PetConf.get().getPetDisplayItems();
        pets.forEach((slot, pet) -> {
            getInventory().setItem(slot, display.get(pet.getType()).getGuiItem());
            this.setAction(slot, event -> {
                event.setCancelled(true);
                player.setActivePet(pet.getType());
                event.getWhoClicked().sendMessage("You have selected the " + pet.getType().name() + " pet.");
                setUpInventory();
                return true;
            });
        });
    }

    public void open() {
        getPlayer(player.getId()).openInventory(getInventory());
    }
}
