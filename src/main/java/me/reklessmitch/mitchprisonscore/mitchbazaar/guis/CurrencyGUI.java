package me.reklessmitch.mitchprisonscore.mitchbazaar.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.util.ItemBuilder;
import me.reklessmitch.mitchprisonscore.utils.LangConf;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CurrencyGUI extends ChestGui {

    Player player;

    public CurrencyGUI(Player player) {
        this.player = player;
        setInventory(Bukkit.createInventory(null, 27, LangConf.get().getBazaarGuiTitle()));
        setup();
        add();
    }

    private void setUpAction(int slot, ItemStack item, String itemToBeBrought){
        getInventory().setItem(slot, item);
        setAction(slot, event -> {
            new PurchaseGUI(item, itemToBeBrought).open(player);
            return true;
        });
    }
    private void setup() {
        setUpAction(10, new ItemBuilder(Material.BEACON).displayname("§eBeacon").lore("§7Click to buy/sell beacons").build(), "beacon");
        setUpAction(12, new ItemBuilder(Material.EMERALD).displayname("§aToken").lore("§7Click to buy/sell tokens").build(), "token");
        setUpAction(14, new ItemBuilder(Material.GOLD_INGOT).displayname("§6Money").lore("§7Click to buy/sell money").build(), "money");
        setUpAction(16, new ItemBuilder(Material.DIAMOND).displayname("§bCredits").lore("§7Click to buy/sell credits").build(), "credits");
    }

    public void open() {
        player.openInventory(getInventory());
    }

}
