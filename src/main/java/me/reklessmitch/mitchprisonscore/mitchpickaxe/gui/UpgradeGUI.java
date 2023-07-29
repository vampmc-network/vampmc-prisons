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
        PickaxeConf.get().getEnchants().forEach((enchant, e) -> {
            int slot = e.getDisplayItem().getSlot();
            getInventory().setItem(slot, e.getEnchantGuiItem(playerPickaxe));
            this.setAction(slot, event -> {
                new UpgradeEnchantGUI(e, player).open();
                return true;
            });
        });
        ItemStack pickaxeSkin = new ItemBuilder(Material.DIAMOND_PICKAXE).displayname("§aPickaxe Skins").glow().modelData(10000).build();
        getInventory().setItem(36, pickaxeSkin);
        this.setAction(36, event -> {
            new PickaxeSkins(player).open();
            return true;
        });
        getInventory().setItem(40, new ItemBuilder(Material.COMPARATOR).displayname("§cTOGGLES").build());
        setAction(40, event -> {
            new TogglesGUI(player).open();
            return true;
        });
    }

    public void open(){
        player.openInventory(getInventory());
    }
}
