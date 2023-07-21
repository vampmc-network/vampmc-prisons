package me.reklessmitch.mitchprisonscore.mitchpickaxe.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.util.ItemBuilder;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.enchants.Enchant;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.currency.MitchCurrency;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffectType;


public class UpgradeEnchantGUI extends ChestGui {

    Enchant enchant;
    Player player;
    PPickaxe p;

    public UpgradeEnchantGUI(Enchant enchant, Player player) {
        this.enchant = enchant;
        this.player = player;
        this.p = PPickaxe.get(player);
        setInventory(Bukkit.createInventory(null, 27, "UPGRADE " + enchant.getType()));
        refresh();
        setAutoclosing(false);
        setSoundOpen(null);
        setSoundClose(null);
        setAutoremoving(true);
        add();
    }

    private void getPane(int slot, int amount, int level){
        if(level + amount > enchant.getMaxLevel()){
            getInventory().setItem(slot, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).build());
            return;
        }
        long cost = enchant.getCost(level, amount);
        getInventory().setItem(slot, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE)
                .displayname("§cUpgrade: §f" + amount)
                .lore("§cCost: §f" + cost)
                .build());
        setAction(slot, event -> {
            event.setCancelled(true);
            MitchCurrency currency = ProfilePlayer.get(player.getUniqueId()).getCurrency("token");
            if(currency.getAmount() - cost > 0){
                currency.take(cost);
                p.getEnchants().replace(enchant.getType(), amount + p.getEnchants().get(enchant.getType()));
                player.sendMessage("§aYou have upgraded " + enchant.getType() + " by " + amount + " levels");
                updatePickaxe(player);
                refresh();
            }else{
                player.sendMessage("§cYou do not have enough tokens to upgrade this enchantment");
            }
            return true;
        });
    }

    private void updatePickaxe(Player effectPlayer){
        p.updatePickaxe();
        p.givePickaxe();
        refresh();
        effectPlayer.removePotionEffect(PotionEffectType.SPEED);
        effectPlayer.removePotionEffect(PotionEffectType.FAST_DIGGING);
    }

    public void refresh(){
        int currentLevel = p.getEnchants().get(enchant.getType());
        Inventory inv = getInventory();
        inv.setItem(4, enchant.getEnchantGuiItem(p));
        getPane(10, 1, currentLevel);
        getPane(11,5, currentLevel);
        getPane(12, 10, currentLevel);
        getPane(13, 25, currentLevel);
        getPane(14, 100, currentLevel);
    }

    public void open(){
        player.openInventory(getInventory());
    }

}
