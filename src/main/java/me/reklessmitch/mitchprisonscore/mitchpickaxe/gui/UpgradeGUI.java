package me.reklessmitch.mitchprisonscore.mitchpickaxe.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.util.ItemBuilder;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PickaxeConf;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.enchants.Enchant;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UpgradeGUI extends ChestGui {

    Player player;

    public UpgradeGUI(Player player) {
        this.setInventory(Bukkit.createInventory(null, 45, "Upgrade Pickaxe"));
        this.player = player;
        add();
        createInventory();
        setAutoclosing(false);
        setSoundOpen(null);
        setSoundClose(null);
    }

    public void createInventory(){
        PPickaxe playerPickaxe = PPickaxe.get(player.getUniqueId());
        getInventory().setItem(4, playerPickaxe.getPickaxe().getGuiItem(player.getUniqueId()));
        playerPickaxe.getEnchants().forEach((enchant, level) -> {
            Enchant e = PickaxeConf.get().getEnchantByType(enchant);
            getInventory().setItem(e.getDisplayItem().getSlot(), e.getEnchantGuiItem(playerPickaxe));
            this.setAction(e.getDisplayItem().getSlot(), event -> {
                event.setCancelled(true);
                new UpgradeEnchantGUI(e, player).open();
                return true;
            });
        });
        ItemStack pickaxeSkin = new ItemBuilder(Material.DIAMOND_PICKAXE).displayname("§aPickaxe Skins").glow().modelData(10000).build();
        getInventory().setItem(36, pickaxeSkin);
        this.setAction(36, event -> {
            event.setCancelled(true);
            new PickaxeSkins(player).open();
            return true;
        });
    }

    public void open(){
        player.openInventory(getInventory());
    }
}
