package me.reklessmitch.mitchprisonscore.mitchpickaxe.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.util.ItemBuilder;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PickaxeSkins extends ChestGui {

    Player player;

    public PickaxeSkins(Player player){
        setInventory(Bukkit.createInventory(null, 9, "Pickaxe Skins"));
        this.player = player;
        setupInventory();
        add();
    }

    private void getPickaxeSkinItem(String name, String lore, int customDataModel, int slot){
        ItemStack item = new ItemBuilder(Material.DIAMOND_PICKAXE)
                .displayname(name)
                .lore(lore)
                .build();
        item.getItemMeta().setCustomModelData(customDataModel);
        getInventory().setItem(slot, item);
        this.setAction(slot, event -> {
            event.setCancelled(true);
            PPickaxe pick = PPickaxe.get(player.getUniqueId());
            if(pick.getPickaxe().getCustomModelData() == customDataModel){
                player.sendMessage("§cYou already have this skin selected!");
                return false;
            }
            player.sendMessage("§aYou have selected the " + name + " skin!");
            pick.setSkin(customDataModel);
            player.closeInventory();
            return true;
        });
    }

    private void setupInventory() {
        getPickaxeSkinItem("§aDefault", "§7The default pickaxe skin.", 0, 0);
        getPickaxeSkinItem("§aEmerald", "§bPENGUIN PICKAXE", 10000, 1);
    }

    public void open(){
        player.openInventory(getInventory());
    }

}
