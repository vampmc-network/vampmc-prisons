package me.reklessmitch.mitchprisonscore.mitchprofiles.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilesConf;
import me.reklessmitch.mitchprisonscore.mitchprofiles.currency.MitchCurrency;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class StoreShop extends ChestGui {

    Player player;
    ProfilePlayer profilePlayer;

    public StoreShop(Player player) {
        this.player = player;
        this.profilePlayer = ProfilePlayer.get(player.getUniqueId());
        setInventory(Bukkit.createInventory(null, 18, "Store"));
        setUpInventory();
        setAutoclosing(false);
        setSoundOpen(null);
        setSoundClose(null);
        add();
    }

    private void setUpInventory() {
        ProfilesConf.get().getCreditShop().forEach(shopItem -> {
            getInventory().setItem(shopItem.getSlot(), shopItem.getGuiItem());
            MitchCurrency currency = profilePlayer.getCurrency("credits");
            int cost = shopItem.getCost();
            setAction(shopItem.getSlot(), event -> {
                if (currency.getAmount() >= cost) {
                    currency.take(cost);
                    profilePlayer.changed();
                    player.sendMessage("§aYou have purchased " + shopItem.getName() + " for " + cost + " credits!");
                    List<String> commands = shopItem.getCommands();
                    commands.replaceAll(s -> s.replace("%player%", player.getName()));
                    commands.forEach(s -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s));
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
