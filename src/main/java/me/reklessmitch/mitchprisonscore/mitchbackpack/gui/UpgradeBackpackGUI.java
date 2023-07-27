package me.reklessmitch.mitchprisonscore.mitchbackpack.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.util.ItemBuilder;

import me.reklessmitch.mitchprisonscore.mitchbackpack.config.BackpackConf;
import me.reklessmitch.mitchprisonscore.mitchbackpack.config.BackpackPlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.currency.MitchCurrency;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UpgradeBackpackGUI extends ChestGui {

    Player player;
    BackpackPlayer backpackPlayer;
    ProfilePlayer profilePlayer;

    public UpgradeBackpackGUI(Player player) {
        this.player = player;
        this.backpackPlayer = BackpackPlayer.get(player.getUniqueId());
        this.profilePlayer = ProfilePlayer.get(player.getUniqueId());
        setInventory(Bukkit.createInventory(null, 45, "Upgrade Backpack"));
        setAutoclosing(false);
        setSoundOpen(null);
        setSoundClose(null);
        refresh();
        add();
    }

    private void getUpgradeItem(int slot, int amount, int cost){
        ItemStack guiItem = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE)
                .displayname("§aUpgrade §c§l" + amount + " §aslots")
                .lore( "§cCost: §f" + cost)
                .build();
        getInventory().setItem(slot, guiItem);
        setAction(slot, event -> {
            MitchCurrency currency = profilePlayer.getCurrency("token");
            if(currency.getAmount() - cost >= 0){
                backpackPlayer.addSlot(amount);
                currency.take(cost);
                refresh();
                return false;
            }else{
                player.sendMessage("§cYou do not have enough tokens to upgrade your backpack");
            }
            return true;
        });
    }
    public void refresh(){
        getInventory().setItem(4, backpackPlayer.getBackpackItem());
        getUpgradeItem(10, 1, backpackPlayer.getCost(1));
        getUpgradeItem(11, 5, backpackPlayer.getCost(5));
        getUpgradeItem(12, 50, backpackPlayer.getCost(50));
        getUpgradeItem(13, 250, backpackPlayer.getCost(250));
        getUpgradeItem(14, 1000, backpackPlayer.getCost(1000));
        getUpgradeItem(15, 10000, backpackPlayer.getCost(10000));
        getUpgradeItem(16, backpackPlayer.getMaxPurchasable(), backpackPlayer.getCost(backpackPlayer.getMaxPurchasable()));
        getBackpackSkinItem();
        getAutoSellItem();
    }

    private void getBackpackSkinItem() {
        getInventory().setItem(36, new ItemBuilder(Material.PAINTING).displayname("§cBackpack Skin")
                .lore("§7Click to edit your backpack skin").modelData(backpackPlayer.getSkinID()).build());
        setAction(36, event -> {
            new BackpackSkins(player).open();
            return true;
        });
    }

    private void getAutoSellItem() {
        ItemStack guiItem = new ItemBuilder(Material.GOLD_BLOCK).displayname("§cAuto Sell")
                .lore("§7Instantly sells your backpack when it fills up!",
                        backpackPlayer.isAutoSell() ? "§aEnabled" : "§cDisabled", "§7 ",
                        "§cCost: §e" + BackpackConf.get().getAutoSellCost()).build();

        getInventory().setItem(44, guiItem);
        setAction(44, event -> {
            event.setCancelled(true);
            MitchCurrency token = profilePlayer.getCurrency("token");
            int cost = BackpackConf.get().getAutoSellCost();
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

    public void open() {
        player.openInventory(getInventory());
    }
}
