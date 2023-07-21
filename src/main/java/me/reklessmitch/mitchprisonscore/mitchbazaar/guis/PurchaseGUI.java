package me.reklessmitch.mitchprisonscore.mitchbazaar.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.util.ItemBuilder;
import me.reklessmitch.mitchprisonscore.mitchbazaar.config.BazaarConf;
import me.reklessmitch.mitchprisonscore.mitchbazaar.object.ShopValue;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilesConf;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class PurchaseGUI extends ChestGui {

    ItemStack item; // item being sold
    Map<String, List<ShopValue>> sellPrices; // prices for item being sold
    String itemToBeBrought; // item being brought

    public PurchaseGUI(ItemStack item, String itemToBeBrought) {
        setInventory(Bukkit.createInventory(null, 27, "Purchase " + itemToBeBrought));
        this.itemToBeBrought = itemToBeBrought;
        this.item = item;
        this.sellPrices = BazaarConf.get().getSellPrices().get(itemToBeBrought);
        setAutoclosing(false);
        setSoundOpen(null);
        setSoundClose(null);
        setup();
        add();
    }

    private Material getCurrency(String itemToBeBrought) {
        return switch (itemToBeBrought) {
            case "beacon" -> Material.EMERALD;
            case "token" -> Material.GOLD_INGOT;
            case "money" -> Material.DIAMOND;
            case "credits" -> Material.BEACON;
            default -> Material.BARRIER;
        };
    }

    private void setup() {
        getInventory().setItem(4, new ItemBuilder(item.getType()).lore("§7Click the currency",
                "§7you want to exchange!").build());
        List<String> currencies = ProfilesConf.get().getCurrencyList();
        for(int i = 0; i < currencies.size(); i++){
            List<ShopValue> sorted = sellPrices.get(currencies.get(i));
            if(sorted == null) {
                getInventory().setItem(10 + i * 2, new ItemStack(Material.BARRIER));
                continue;
            }
            sorted.sort(Comparator.comparing(ShopValue::getPricePerItem));
            getInventory().setItem(10 + i * 2, new ItemBuilder(getCurrency(currencies.get(i)))
                    .displayname("§e" + currencies.get(i).toUpperCase())
                    .lore("§7Price per item" + (sorted.isEmpty() ? ": §cN/A" : ": §a" + sorted.get(0).getPricePerItem()))
                    .build());
            setAction(10 + i * 2, event -> {
                Bukkit.broadcastMessage("Clicked, attempt to buy item at" +  sorted.get(0).getPricePerItem());
                return true;
            });
        }
    }

    public void open(Player player) {
        player.openInventory(getInventory());
    }
}
