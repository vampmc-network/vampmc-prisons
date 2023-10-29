package me.reklessmitch.mitchprisonscore.mitchprofiles.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import me.reklessmitch.mitchprisonscore.mitchpets.util.DisplayItem;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilesConf;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

public class StoreCategories extends ChestGui {

    private final Map<String, DisplayItem> shops;

    public StoreCategories() {
        this.shops = ProfilesConf.get().getStoreCategories();
        this.setInventory(Bukkit.createInventory(null, 18, "Store Categories"));
        this.setUpInventory();
        add();
    }

    private void setUpInventory() {
        shops.forEach((s, displayItem) -> {
            this.getInventory().setItem(displayItem.getSlot(), displayItem.getGuiItem());
            setAction(displayItem.getSlot(), event -> {
                new StoreShop((Player) event.getWhoClicked(), s).open();
                return true;
            });
        });
    }

    public void open(Player player){
        player.openInventory(this.getInventory());
    }

}
