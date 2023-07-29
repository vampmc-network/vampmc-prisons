package me.reklessmitch.mitchprisonscore.mitchpickaxe.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PickaxeConf;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.enchants.Enchant;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TogglesGUI extends ChestGui {

    private final Player player;
    private final PPickaxe pickaxe;

    public TogglesGUI(@NotNull  Player player) {
        setInventory(Bukkit.createInventory(player, 45, "Pickaxe Toggles"));
        this.player = player;
        this.pickaxe = PPickaxe.get(player.getUniqueId());
        refresh();
        setAutoclosing(false);
        setSoundOpen(null);
        setSoundClose(null);
        add();
    }

    public void refresh(){
        getInventory().setItem(4, pickaxe.getPickaxe().getGuiItem(player.getUniqueId()));
        PickaxeConf.get().getEnchants().forEach((enchant, level) -> {
            Enchant e = PickaxeConf.get().getEnchantByType(enchant);
            getInventory().setItem(e.getDisplayItem().getSlot(), e.getEnchantGuiToggleItem(pickaxe));
            setAction(e.getDisplayItem().getSlot(), event -> {
                event.setCancelled(true);
                pickaxe.toggleEnchant(enchant);
                refresh();
                return true;
            });
        });
    }

    public void open(){
        player.openInventory(getInventory());
    }
}
