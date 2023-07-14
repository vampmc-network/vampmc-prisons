package me.reklessmitch.mitchprisonscore.mitchmines.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.MineConf;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.PlayerMine;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BlockGUI extends ChestGui {

    Player player;
    public BlockGUI(Player player){
        this.player = player;
        setInventory(Bukkit.createInventory(null, 45, "Set Block"));
        init();
        add();
    }

    public void init() {
        int i = 0;
        for(Material block : MineConf.get().getBlockMap()){
            getInventory().setItem(i, new ItemStack(block));
            setAction(i, event -> {
                PlayerMine.get(player.getUniqueId()).setBlock(block);
                return true;
            });
        }
    }

    public void open() {
        player.openInventory(getInventory());
    }
}
