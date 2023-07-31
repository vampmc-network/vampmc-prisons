package me.reklessmitch.mitchprisonscore.mitchpickaxe.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PickaxeConf;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TogglesGUI extends ChestGui {

    private final Player player;
    private final PPickaxe pickaxe;
    private final boolean toggle; // true = Enchant Toggles, false = Enchant Message Toggles

    public TogglesGUI(@NotNull  Player player, boolean toggle) {
        this.toggle = toggle;
        this.player = player;
        this.pickaxe = PPickaxe.get(player.getUniqueId());
        setInventory(Bukkit.createInventory(player, 45, "Pickaxe Toggles"));
        refresh();
        setAutoclosing(false);
        setSoundOpen(null);
        setSoundClose(null);
        add();
    }

    public void refresh(){
        getInventory().setItem(4, pickaxe.getPickaxeGuiItem());
        PickaxeConf.get().getEnchants().forEach((type, e) -> {
            int slot = e.getDisplayItem().getSlot();
            if(toggle) {
                getInventory().setItem(slot, e.getEnchantGuiToggleItem(pickaxe));
                setAction(slot, event -> {
                    event.setCancelled(true);
                    pickaxe.toggleEnchant(type);
                    refresh();
                    return true;
                });
            }else{
                getInventory().setItem(slot, e.getEnchantMessageToggleItem(pickaxe));
                setAction(slot, event -> {
                    event.setCancelled(true);
                    pickaxe.toggleEnchantMessage(type);
                    refresh();
                    return true;
                });
            }
        });
    }

    public void open(){
        player.openInventory(getInventory());
    }
}
