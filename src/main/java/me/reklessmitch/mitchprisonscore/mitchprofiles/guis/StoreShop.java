package me.reklessmitch.mitchprisonscore.mitchprofiles.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import me.reklessmitch.mitchprisonscore.mitchpets.util.DisplayItem;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilesConf;
import me.reklessmitch.mitchprisonscore.mitchprofiles.currency.MitchCurrency;
import me.reklessmitch.mitchprisonscore.mitchprofiles.object.ShopItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class StoreShop extends ChestGui {

    private final Player player;
    private final ProfilePlayer profilePlayer;
    Map<DisplayItem, List<ShopItem>> shopItems;

    public StoreShop(Player player, String store) {
        this.shopItems = ProfilesConf.get().getShops().get(store);
        this.player = player;
        this.profilePlayer = ProfilePlayer.get(player.getUniqueId());
        setInventory(Bukkit.createInventory(null, 18, store));
        setUpInventory();
        setAutoclosing(false);
        setSoundOpen(null);
        setSoundClose(null);
        add();
    }

    private void setUpInventory() {
        MitchCurrency currency = profilePlayer.getCurrency("credits");
        shopItems.forEach((s, shopItemList) -> shopItemList.forEach(shopItem -> {
            this.getInventory().setItem(shopItem.getSlot(), shopItem.getGuiItem());
            int cost = shopItem.getCost();
            setAction(shopItem.getSlot(), event -> {
                if (currency.getAmount() >= cost) {
                    currency.take(cost);
                    profilePlayer.changed();
                    player.sendMessage("§aYou have purchased " + shopItem.getName() + " for " + cost + " credits!");
                    List<String> commands = shopItem.getCommands();
                    commands.replaceAll(c -> c.replace("%player%", player.getName()));
                    commands.forEach(c -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c));
                } else {
                    player.sendMessage("§cYou do not have enough credits to purchase this item!");
                }
                return true;
            });
        }));
    }

    public void open() {
        player.openInventory(getInventory());
    }
}
