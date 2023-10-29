package me.reklessmitch.mitchprisonscore.mitchbazaar.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.util.ItemBuilder;
import me.reklessmitch.mitchprisonscore.MitchPrisonsCore;
import me.reklessmitch.mitchprisonscore.mitchbazaar.config.BazaarConf;
import me.reklessmitch.mitchprisonscore.mitchbazaar.object.ShopValue;
import me.reklessmitch.mitchprisonscore.mitchbazaar.runnables.SignOverGUI;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilesConf;
import me.reklessmitch.mitchprisonscore.utils.LangConf;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class PurchaseGUI extends ChestGui {

    private final ItemStack item; // item being sold
    private final Map<String, List<ShopValue>> sellPrices; // prices for item being sold
    private final String itemToBeBrought; // item being brought

    public PurchaseGUI(ItemStack item, String itemToBeBrought) {
        setInventory(Bukkit.createInventory(null, 27, LangConf.get().getBazaarGuiTitle()));
        this.itemToBeBrought = itemToBeBrought;
        this.item = item;
        this.sellPrices = BazaarConf.get().getSellPrices().get(itemToBeBrought);
        setAutoclosing(false);
        setSoundOpen(null);
        setSoundClose(null);
        setup();
        add();
    }


    private void setup() {
        getInventory().setItem(4, new ItemBuilder(item.getType()).lore("§7Click the currency", "§7you want to exchange!").build());
        List<String> currencies = ProfilesConf.get().getCurrencyList();
        for(int i = 0; i < currencies.size(); i++){
            String currency = currencies.get(i);
            List<ShopValue> sorted = sellPrices.get(currency);
            if(sorted == null) {
                getInventory().setItem(10 + i * 2, new ItemStack(Material.BARRIER));
                continue;
            }
            sorted.sort(Comparator.comparing(ShopValue::getPricePerItem));
            BigInteger totalStock = sorted.stream()
                    .map(ShopValue::getAmount)
                    .map(BigInteger::valueOf) // Convert long to BigInteger
                    .reduce(BigInteger.ZERO, BigInteger::add); // Sum all values
            getInventory().setItem(10 + i * 2, new ItemBuilder(BazaarConf.get().getCurrency(currency))
                    .displayname("§e" + currencies.get(i).toUpperCase())
                    .lore("§7Total Stock: §c" + totalStock,
                            "§7Lowest Price" + (sorted.isEmpty() ? ": §cN/A" :
                            "§a: " + sorted.get(0).getAmount() + " @ " +
                                    MitchPrisonsCore.get().getDecimalFormat().format(sorted.get(0).getPricePerItem()) + "per"))
                    .build());


            setAction(10 + i * 2, event -> {
                event.getWhoClicked().closeInventory();
                new SignOverGUI((Player) event.getWhoClicked(), itemToBeBrought, currency, totalStock).runTask(MitchPrisonsCore.get());
                return true;
            });
        }
    }

    public void open(Player player) {
        player.openInventory(getInventory());
    }
}
