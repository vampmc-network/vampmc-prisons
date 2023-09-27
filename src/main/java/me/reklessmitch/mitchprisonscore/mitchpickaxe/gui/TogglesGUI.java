package me.reklessmitch.mitchprisonscore.mitchpickaxe.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PickaxeConf;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.enchants.Enchant;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.utils.EnchantType;
import me.reklessmitch.mitchprisonscore.utils.LangConf;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class TogglesGUI extends ChestGui {

    private final Player player;
    private final PPickaxe pickaxe;
    private final int toggle;// true = Enchant Toggles, false = Enchant Message Toggles

    public TogglesGUI(@NotNull Player player, int toggle) {
        this.toggle = toggle;
        this.player = player;
        this.pickaxe = PPickaxe.get(player.getUniqueId());
        setInventory(Bukkit.createInventory(null, 27, LangConf.get().getPickaxeTogglesGuiTitle()));
        refresh();
        setAutoclosing(false);
        setSoundOpen(null);
        setSoundClose(null);
        add();
    }

    public void refresh(){
        int startSlot = 0;
        for (Map.Entry<EnchantType, Enchant> entry : PickaxeConf.get().getEnchants().entrySet()) {
            EnchantType type = entry.getKey();

            getInventory().setItem(startSlot, entry.getValue().getEnchantGuiToggleItem(pickaxe));

            this.setAction(startSlot, event -> {
                event.setCancelled(true);

                switch (toggle) {
                    case 0 -> pickaxe.toggleEnchant(type);
                    case 1 -> pickaxe.toggleEnchantMessage(type);
                    case 2 -> pickaxe.toggleEnchantSound(type);
                    default -> {
                    }
                }

                refresh();
                return true;
            });

            startSlot++;
        }
    }

    public void open(){
        player.openInventory(getInventory());
    }
}
