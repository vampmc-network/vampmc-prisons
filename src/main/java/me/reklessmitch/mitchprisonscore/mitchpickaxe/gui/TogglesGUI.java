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

    public TogglesGUI(@NotNull Player player, boolean toggle) {
        this.toggle = toggle;
        this.player = player;
        this.pickaxe = PPickaxe.get(player.getUniqueId());
        setInventory(Bukkit.createInventory(null, 27, PickaxeConf.get().getPickaxeTogglesGuiTitle()));
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
            getInventory().setItem(slot, e.getEnchantGuiToggleItem(pickaxe));
            this.setAction(slot, event -> {
                event.setCancelled(true);
                if(toggle) {
                    pickaxe.toggleEnchant(type);
                }else{
                    pickaxe.toggleEnchantMessage(type);
                }
                refresh();
                return true;
            });
        });
    }

    public void open(){
        player.openInventory(getInventory());
    }
}
