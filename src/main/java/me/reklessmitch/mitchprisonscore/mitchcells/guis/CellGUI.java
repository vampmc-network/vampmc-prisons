package me.reklessmitch.mitchprisonscore.mitchcells.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import me.reklessmitch.mitchprisonscore.mitchcells.configs.CellConf;
import me.reklessmitch.mitchprisonscore.mitchcells.object.DisplayItemCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class CellGUI extends ChestGui {

    private final List<DisplayItemCommand> guiItems;

    public CellGUI() {
        guiItems = CellConf.get().getGuiItems();
        setInventory(Bukkit.createInventory(null, CellConf.get().getSize(), "Cell Menu"));
        init();
        add();
    }

    public void init() {
        guiItems.forEach(item -> {
            getInventory().setItem(item.getSlot(), item.getGuiItem());
            this.setAction(item.getSlot(), event -> {
                item.runCommand((Player) event.getWhoClicked());
                return true;
            });
        });
    }

    public void open(Player player) {
        player.openInventory(getInventory());
    }

}
