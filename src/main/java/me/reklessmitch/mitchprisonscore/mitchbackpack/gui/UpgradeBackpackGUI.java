package me.reklessmitch.mitchprisonscore.mitchbackpack.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.util.ItemBuilder;

import me.reklessmitch.mitchprisonscore.mitchbackpack.config.BackpackConf;
import me.reklessmitch.mitchprisonscore.mitchbackpack.config.BackpackPlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.currency.MitchCurrency;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.CurrencyUtils;
import me.reklessmitch.mitchprisonscore.utils.LangConf;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UpgradeBackpackGUI extends ChestGui {

    private final Player player;
    private final BackpackPlayer backpackPlayer;
    private final ProfilePlayer profilePlayer;

    public UpgradeBackpackGUI(Player player) {
        this.player = player;
        this.backpackPlayer = BackpackPlayer.get(player.getUniqueId());
        this.profilePlayer = ProfilePlayer.get(player.getUniqueId());
        setInventory(Bukkit.createInventory(null, 54, LangConf.get().getBackpackGuiTitle()));
        setAutoclosing(false);
        setSoundOpen(null);
        setSoundClose(null);
        refresh();
        add();
    }

    private void getUpgradeItem(int[] slots, long amount, long cost){
        MitchCurrency currency = profilePlayer.getCurrency("token");
        ItemStack item = new ItemBuilder(Material.PAPER)
                .displayname("§aUpgrade §c§l" + amount + " §aslots")
                .lore("§cCost: §f" + CurrencyUtils.format(cost))
                .modelData(10006)
                .build();
        for(int slot : slots) {
            getInventory().setItem(slot, item);
            this.setAction(slot, event -> {
                if (currency.getAmount() - cost >= 0) {
                    backpackPlayer.addSlot(amount);
                    currency.take(cost);
                    refresh();
                    return false;
                } else {
                    player.sendMessage("§cYou do not have enough tokens to upgrade your backpack");
                }
                return true;
            });
        }
    }
    public void refresh(){
        getUpgradeItem(new int[]{0,1,2,9,10,11}, 1, backpackPlayer.getCost(1));
        getUpgradeItem(new int[]{3,4,5,12,13,14}, 5, backpackPlayer.getCost(10));
        getUpgradeItem(new int[]{6,7,8,15,16,17}, 50, backpackPlayer.getCost(50));
        getUpgradeItem(new int[]{18,19,20,27,28,29}, 500, backpackPlayer.getCost(500));
        getUpgradeItem(new int[]{21,22,23,30,31,32}, 1000, backpackPlayer.getCost(5000));
        getUpgradeItem(new int[]{24,25,26,33,34,35}, backpackPlayer.getMaxPurchasable(), backpackPlayer.getCost(backpackPlayer.getMaxPurchasable()));
        getBackpackSkinItem();
        getAutoSellItem();
        togglesButton();
    }

    private void getBackpackSkinItem() {
        int[] slots = new int[]{36,37,38,45,46,47};
        ItemStack item = new ItemBuilder(Material.PAPER).displayname("§cBackpack Skin").modelData(10006)
                .lore("§7Click to edit your backpack skin").build();
        for(int slot: slots){
            getInventory().setItem(slot, item);
            this.setAction(slot, event -> {
                new BackpackSkins(player).open();
                return true;
            });
        }
    }

    private void togglesButton(){
        int[] slots = new int[]{41,42,43,44,50, 51, 52, 53};
        ItemStack item = new ItemBuilder(Material.PAPER).displayname("§cToggles").modelData(10006)
                .lore("§7Click to toggle backpack messages!").build();
        for(int slot: slots){
            getInventory().setItem(slot, item);
            this.setAction(slot, event -> {
                backpackPlayer.setMessages(!backpackPlayer.isMessages());
                player.sendMessage("§aYou have toggled backpack messages " + (backpackPlayer.isMessages() ? "§aon" : "§coff"));
                event.setCancelled(true);
                return true;
            });
        }
    }

    private void getAutoSellItem() {
        int[] slots = new int[]{39,40,48,49};
        long cost = BackpackConf.get().getAutoSellCost();
        MitchCurrency token = profilePlayer.getCurrency("token");
        ItemStack item = new ItemBuilder(Material.PAPER).displayname("§cAuto Sell").modelData(10006)
                .lore("§7Instantly sells your backpack when it fills up!", backpackPlayer.isAutoSell() ?
                                "§aEnabled" : "§cDisabled", "§7 ",
                        "§cCost: §e" + BackpackConf.get().getAutoSellCost()).build();
        for(int slot: slots){
            getInventory().setItem(slot, item);
            this.setAction(slot, event -> {
                event.setCancelled(true);
                if(backpackPlayer.isAutoSell()){
                    player.sendMessage("§cYou already have auto sell!");
                    return false;
                }
                if(token.getAmount() < cost){
                    player.sendMessage("§cYou do not have enough tokens to purchase auto sell");
                    return false;
                }
                token.take(cost);
                profilePlayer.changed();
                backpackPlayer.setAutoSell(true);
                player.sendMessage("§aYou have purchased auto sell for " + cost + " tokens");
                refresh();
                return true;
            });
        }
    }

    public void open() {
        player.openInventory(getInventory());
    }
}
