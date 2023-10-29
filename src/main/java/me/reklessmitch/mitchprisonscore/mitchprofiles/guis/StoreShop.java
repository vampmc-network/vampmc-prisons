package me.reklessmitch.mitchprisonscore.mitchprofiles.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilesConf;
import me.reklessmitch.mitchprisonscore.mitchprofiles.currency.MitchCurrency;
import me.reklessmitch.mitchprisonscore.mitchprofiles.object.ShopItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class StoreShop extends ChestGui {

    private final Player player;
    private final ProfilePlayer profilePlayer;
    List<ShopItem> shopItems;

    public StoreShop(Player player, String store) {
        this.shopItems = ProfilesConf.get().getStoreItems().get(store);
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
        shopItems.forEach(shopItem -> {
            this.getInventory().setItem(shopItem.getSlot(), shopItem.getGuiItem());
            int cost = shopItem.getCost();
            this.setAction(shopItem.getSlot(), event -> {
                if (currency.getAmount().compareTo(BigInteger.valueOf(cost)) >= 0) {
                    currency.take(cost);
                    profilePlayer.changed();
                    player.sendMessage("§aYou have purchased " + shopItem.getName() + " for " + cost + " credits!");
                    List<String> commands = new ArrayList<>(shopItem.getCommands());
                    commands.replaceAll(c -> c.replace("%player%", player.getName()));
                    commands.forEach(c -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c));
                } else {
                    player.sendMessage("§cYou do not have enough credits to purchase this item!");
                }
                return true;
            });
        });
    }

    public void open() {
        player.openInventory(getInventory());
    }
}
