package me.reklessmitch.mitchprisonscore.mitchpickaxe.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.util.ItemBuilder;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;

import me.reklessmitch.mitchprisonscore.mitchpickaxe.enchants.Enchant;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.currency.MitchCurrency;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.CurrencyUtils;
import me.reklessmitch.mitchprisonscore.utils.LangConf;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;


public class PrestigeEnchantGUI extends ChestGui {


    private final Enchant enchant;
    private final Player player;
    private final PPickaxe p;

    public PrestigeEnchantGUI(@NotNull Enchant enchant, Player player) {
        this.enchant = enchant;
        this.player = player;
        this.p = PPickaxe.get(player);
        setInventory(Bukkit.createInventory(null, 9, "§aConfirm prestige"));
        refresh();
        setAutoclosing(true);
        setSoundOpen(null);
        setSoundClose(null);
        setAutoremoving(true);
        add();
    }

    public void refresh(){
        getInventory().setItem(4, enchant.getEnchantGuiItem(p));
        for(int i = 0; i < 4; i++) {
            getInventory().setItem(i, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE)
                    .displayname("§aConfirm prestige")
                    .lore("§aClick to confirm the prestige of " + enchant.getType().name())
                    .build());
            setAction(i, event -> {
                player.sendMessage("§aYou have prestiged the enchantment " + enchant.getType().name());
                p.getEnchants().replace(enchant.getType(), 0);
                p.getEnchantPrestiges().replace(enchant.getType(), p.getEnchantPrestiges().get(enchant.getType()) + 1);
                p.updatePickaxe();
                return true;
            });
        }

        for(int i = 5; i < 9; i++) {
            getInventory().setItem(i, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).displayname("§cCancel Prestige").build());
            setAction(i, event -> {
                player.sendMessage("§cYou have cancelled the prestige.");
                return true;
            });
        }
    }

    public void open(){
        player.openInventory(getInventory());
    }

}
