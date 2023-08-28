package me.reklessmitch.mitchprisonscore.mitchpickaxe.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.util.ItemBuilder;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;

import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PickaxeConf;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.enchants.Enchant;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.currency.MitchCurrency;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.CurrencyUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class UpgradeEnchantGUI extends ChestGui {

    private final Enchant enchant;
    private final Player player;
    private final PPickaxe p;
    private final ProfilePlayer profilePlayer;

    public UpgradeEnchantGUI(@NotNull Enchant enchant, Player player) {
        this.enchant = enchant;
        this.player = player;
        this.p = PPickaxe.get(player);
        this.profilePlayer = ProfilePlayer.get(player.getUniqueId());
        setInventory(Bukkit.createInventory(null, 54, PickaxeConf.get().getEnchantGuiTitle()));
        refresh();
        setAutoclosing(false);
        setSoundOpen(null);
        setSoundClose(null);
        setAutoremoving(true);
        add();
    }

    private void getPane(int[] slots, int amount, int level){
        long cost = enchant.getCost(level, amount);
        MitchCurrency currency = profilePlayer.getCurrency("token");
        for (int slot : slots) {
            getInventory().setItem(slot, new ItemBuilder(Material.PAPER)
                .displayname("§cUpgrade: §f" + amount)
                .modelData(10006)
                .lore("§cCost: §f" + CurrencyUtils.format(cost))
                .build());
            setAction(slot, event -> {
                event.setCancelled(true);
                if(currency.getAmount() - cost > 0){
                    currency.take(cost);
                    profilePlayer.changed();
                    p.getEnchants().replace(enchant.getType(), amount + p.getEnchants().get(enchant.getType()));
                    player.sendMessage("§aYou have upgraded " + enchant.getType() + " by " + amount + " levels");
                    p.updatePickaxe();
                    refresh();
                }else{
                    player.sendMessage("§cYou do not have enough tokens to upgrade this enchantment");
                }
                return true;
            });
        }
    }


    public void refresh(){
        int currentLevel = p.getEnchants().get(enchant.getType());
        getInventory().setItem(4, enchant.getEnchantGuiItem(p));
        getPane(new int[]{9, 10, 11, 18, 19, 20}, 1, currentLevel);
        getPane(new int[]{12, 13, 14, 21, 22, 23},5, currentLevel);
        getPane(new int[]{15, 16, 17, 24, 25, 26}, 50, currentLevel);
        getPane(new int[]{27, 28, 29, 36, 37, 38}, 500, currentLevel);
        getPane(new int[]{30, 31, 32, 39, 40, 41}, 5000, currentLevel);
        int maxLevels = enchant.getMaxAmount(currentLevel, profilePlayer.getCurrency("token").getAmount(), enchant.getMaxLevel());
        getPane(new int[]{33, 34, 35, 42, 43, 44}, maxLevels, currentLevel);
    }

    public void open(){
        player.openInventory(getInventory());
    }

}
