package me.reklessmitch.mitchprisonscore.mitchpickaxe.gui;

import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.util.ItemBuilder;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import me.reklessmitch.mitchprisonscore.utils.LangConf;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PickaxeSkins extends ChestGui {

    private final Player player;

    public PickaxeSkins(Player player){
        setInventory(Bukkit.createInventory(null, 27, LangConf.get().getPickaxeSkinsGuiTitle()));
        this.player = player;
        setupInventory();
        setAutoclosing(false);
        setSoundOpen(null);
        setSoundClose(null);
        add();
    }

    private void getPickaxeSkinItem(String name, String lore, int customDataModel, int slot){
        getInventory().setItem(slot, new ItemBuilder(Material.DIAMOND_PICKAXE)
                .displayname(name)
                .lore(lore)
                .modelData(customDataModel)
                .build());
        this.setAction(slot, event -> {
            event.setCancelled(true);
            PPickaxe pick = PPickaxe.get(player.getUniqueId());
            if(!player.hasPermission("mitchprisonscore.pickaxe." + customDataModel)){
                player.sendMessage("§cYou do not have permission to use this skin!");
                return false;
            }
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
        getPickaxeSkinItem("§bPenguin Pickaxe", "§7§oAdorned with a charming penguin perched atop,\n" + "§7§oit brings arctic charm and efficiency to your mining expeditions.", 10000, 1);
        getPickaxeSkinItem("§bAbyss Pickaxe", "§7§oDelve into the abyss with this obsidian-black pickaxe,\n" + "§7§oIts eerie, glowing accents add an otherworldly touch.", 10021, 2);
        getPickaxeSkinItem("§bAngel Pickaxe", "§b ", 10022, 3);
        getPickaxeSkinItem("§bNitro Pickaxe", "§b ", 10001, 4);
        getPickaxeSkinItem("§bLittle Cat Pickaxe", "§b ", 10034, 5);
        getPickaxeSkinItem("§bDarkFlame Pickaxe", "§b ", 10036, 6);
        getPickaxeSkinItem("§bDiablo Pickaxe", "§b ", 10037, 7);
        getPickaxeSkinItem("§bAzgard Pickaxe", "§b ", 10024, 8);
        getPickaxeSkinItem("§bDragon Soul", "§b ", 10028, 9);
        getPickaxeSkinItem("§bValerie Pickaxe", "§b ", 10030, 10);
        getPickaxeSkinItem("§bLunar Pickaxe", "§b ", 10035, 11);
        getPickaxeSkinItem("§bAkira Pickaxe", "§b ", 10026, 12);
        getPickaxeSkinItem("§bShark Pickaxe", "§b ", 10033, 13);
        getPickaxeSkinItem("§bChronicles Pickaxe", "§b ", 10032, 14);
        getPickaxeSkinItem("§bThunderbolt Pickaxe", "§b ", 10025, 15);
        getPickaxeSkinItem("§bDuck Pickaxe", "§b ", 10023, 16);
        getPickaxeSkinItem("§bNexus Pickaxe", "§b ", 10032, 17);
        getPickaxeSkinItem("§bRoman Fantasy Pickaxe", "§b ", 10027, 18);
    }

    public void open(){
        player.openInventory(getInventory());
    }

}
