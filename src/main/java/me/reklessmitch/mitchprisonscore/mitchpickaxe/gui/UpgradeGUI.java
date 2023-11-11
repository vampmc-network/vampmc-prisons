package me.reklessmitch.mitchprisonscore.mitchpickaxe.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.util.ItemBuilder;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PickaxeConf;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.utils.LangConf;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UpgradeGUI extends ChestGui {

    private final Player player;

    public UpgradeGUI(Player player) {
        this.setInventory(Bukkit.createInventory(null, 45, LangConf.get().getPickaxeMainGuiTitle()));
        this.player = player;
        add();
        createInventory();
        setSoundOpen(null);
        setSoundClose(null);
    }

    public void createInventory(){
        PPickaxe pPickaxe = PPickaxe.get(player.getUniqueId());
        PickaxeConf.get().getEnchants().forEach((enchant, e) -> {
            getInventory().setItem(e.getDisplayItem().getSlot(), e.getEnchantGuiItem(pPickaxe));
            if(e.getMaxLevel() == pPickaxe.getEnchants().get(enchant)){
                this.setAction(e.getDisplayItem().getSlot(), event -> {
                    if(e.getMaxPrestige() == pPickaxe.getEnchants().get(enchant)) {
                        player.sendMessage("§cYou have already maxed out this enchantment");
                    }else{
                        new PrestigeEnchantGUI(e, player).open();
                    }
                    return true;
                });
                return;
            }
            this.setAction(e.getDisplayItem().getSlot(), event -> {
                if(e.getLevelRequired() > ProfilePlayer.get(player.getUniqueId()).getRank()){
                    player.sendMessage("§cYou do not have the required rank to upgrade this enchantment");
                    return true;
                }
                new UpgradeEnchantGUI(e, player).open();
                return true;
            });
        });
        setupPickaxeSkinButton();
        setupTogglesButton();
    }

    private void setupTogglesButton() {
        int[] toggleSlots = {36, 37, 38, 39, 27, 28, 29, 30};
        ItemStack toggleGuiItem = new ItemBuilder(Material.PAPER).modelData(10006).displayname("§aToggles").lore(
                "§7Click to change your toggles").build();
        for(int slot : toggleSlots){
            getInventory().setItem(slot, toggleGuiItem);
            this.setAction(slot, event -> {
                new TogglesMainGUI((Player) event.getWhoClicked()).open();
                return true;
            });
        }
    }

    private void setupPickaxeSkinButton(){
        int[] pickaxeSkinSlots = {41, 42, 43, 44, 32, 33, 34, 35};
        ItemStack skinGuiItem = new ItemBuilder(Material.PAPER).modelData(10006).displayname("§aPickaxe Skins").lore(
                "§7Click to change your pickaxe skin").build();
        for(int slot : pickaxeSkinSlots){
            getInventory().setItem(slot, skinGuiItem);
            this.setAction(slot, event -> {
                new PickaxeSkins(player).open();
                return true;
            });
        }
    }
    public void open(){
        player.openInventory(getInventory());
    }

}
