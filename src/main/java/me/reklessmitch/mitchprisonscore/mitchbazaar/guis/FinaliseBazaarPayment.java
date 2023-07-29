package me.reklessmitch.mitchprisonscore.mitchbazaar.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.util.IdUtil;
import com.massivecraft.massivecore.util.ItemBuilder;
import me.reklessmitch.mitchprisonscore.mitchbazaar.config.BazaarConf;
import me.reklessmitch.mitchprisonscore.mitchbazaar.object.ShopValue;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.CurrencyUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class FinaliseBazaarPayment extends ChestGui {

    private final long amount;
    private final String itemToBeBrought;
    private final String currencyToBuyWith;
    private Map<ShopValue, Long> cheapestItems = new HashMap<>();
    private final Player player;


    public FinaliseBazaarPayment(Player player, long amount, String itemToBeBrought, String currencyToBuyWith) {
        this.player = player;
        this.itemToBeBrought = itemToBeBrought;
        this.currencyToBuyWith = currencyToBuyWith;
        this.amount = amount;
        setInventory(Bukkit.createInventory(null, 9, "Finalise Payment"));
        perform();
        add();
        setAutoclosing(true);
    }

    public void perform() {
        long cost = getCost();
        for(int i = 0; i < 4; i++) {
            getInventory().setItem(i, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).displayname("§aConfirm Payment").build());
            setAction(i, event -> {
                ProfilePlayer.get(player.getUniqueId()).getCurrency(currencyToBuyWith).take(cost);
                player.sendMessage("§aYou have brought " + amount + " " + itemToBeBrought + " for " + CurrencyUtils.format(cost) + "§e" + currencyToBuyWith + "/s§a.");
                confirmPayment();
                return true;
            });
        }
        getInventory().setItem(4, getGuiItem(cost));
        for(int i = 5; i < 9; i++) {
            getInventory().setItem(i, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).displayname("§cCancel Payment").build());
            setAction(i, event -> {
                player.sendMessage("§cYou have cancelled the payment.");
                return true;
            });
        }
    }

    private void confirmPayment() {
        cheapestItems.forEach((shopValue, a) -> {
            OfflinePlayer p = IdUtil.getOfflinePlayer(shopValue.getOwner());
            long costOfItem = (long) (a * shopValue.getPricePerItem());
            ProfilePlayer.get(shopValue.getOwner()).getCurrency(currencyToBuyWith).add(costOfItem);
            if(p.isOnline()){
                p.getPlayer().sendMessage("§aYour item has been brought from the bazaar! ( "+ costOfItem + " " + currencyToBuyWith + " )");
            }
            long newAmount = shopValue.getAmount() - a;
            BazaarConf conf = BazaarConf.get();
            if(newAmount <= 0){
                conf.getSellPrices().get(itemToBeBrought).get(currencyToBuyWith).remove(shopValue);
            }else {
                shopValue.setAmount(newAmount);
            }
            conf.changed();
        });

    }

    private ItemStack getGuiItem(long cost) {
        return new ItemBuilder(BazaarConf.get().getCurrency(itemToBeBrought)).lore(
                "§7You are about to buy/sell §e" + amount + " " + itemToBeBrought + "§7 for " +
                        CurrencyUtils.format(cost) + "§e" + currencyToBuyWith + "/s§7.",
                "§7Click the §aGreen §7to confirm or the §cRed §7to cancel."
        ).build();
    }

    private long getCost(){
        cheapestItems = new HashMap<>();
        List<ShopValue> shopPrices = BazaarConf.get().getSellPrices().get(itemToBeBrought).get(currencyToBuyWith);
        shopPrices.sort(Comparator.comparing(ShopValue::getPricePerItem));
        long x = amount;
        long cost = 0;
        for (ShopValue item : shopPrices) {
            long quantityToAdd = Math.min(x, item.getAmount());
            cost += quantityToAdd * item.getPricePerItem();
            cheapestItems.put(item, quantityToAdd);
            x -= quantityToAdd;
            if (x <= 0) {
                break;
            }
        }
        return cost;
    }

    public void open() {
        player.openInventory(getInventory());
    }
}
