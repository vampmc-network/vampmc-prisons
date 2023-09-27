package me.reklessmitch.mitchprisonscore.mitchprofiles.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import me.reklessmitch.mitchprisonscore.mitchpets.util.DisplayItem;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilesConf;
import me.reklessmitch.mitchprisonscore.mitchprofiles.object.ShopItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class StoreCategories extends ChestGui {

    private final Map<String, Map<DisplayItem, List<ShopItem>>> shops;

    public StoreCategories() {
        this.shops = ProfilesConf.get().getShops();
        this.setInventory(Bukkit.createInventory(null, 18, "Store Categories"));
        this.setUpInventory();
        add();
    }

    private void setUpInventory() {
        Set<String> stores = shops.keySet();
        stores.forEach(s -> shops.get(s).forEach((displayItem, shopItems) -> {
            this.getInventory().setItem(displayItem.getSlot(), displayItem.getGuiItem());
            this.setAction(displayItem.getSlot(), event -> {
                new StoreShop((Player) event.getWhoClicked(), s).open();
                return true;
            });
        }));

    }

    public void open(Player player){
        player.openInventory(this.getInventory());
    }

}
