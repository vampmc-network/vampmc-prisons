package me.reklessmitch.mitchprisonscore.mitchmines.guis;

import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.util.ItemBuilder;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.MineConf;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.PlayerMine;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.currency.MitchCurrency;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MineGUI extends ChestGui {

    private final Player player;
    private final PlayerMine playerMine;

    public MineGUI(Player player){
        this.player = player;
        this.playerMine = PlayerMine.get(player.getUniqueId());
        setInventory(Bukkit.createInventory(null, 54, MineConf.get().getGuiTitle()));
        init();
        setAutoclosing(false);
        add();
    }

    private void addMineInformationItem(){
        int[] slots = {0, 1, 2, 9, 10, 11, 18, 19, 20, 27, 28, 29, 36, 37, 38, 45, 46, 47};
        for(int slot: slots) {
            getInventory().setItem(slot, new ItemBuilder(Material.PAPER).displayname("§aMine GO").modelData(10006)
                    .lore("§cMine Size: §f" + playerMine.getSize(),
                            "§cMine Block: §f" + playerMine.getBlock().name(),
                            "§cMine Booster: §f" + playerMine.getBooster(),
                            "§7",
                            "§aClick here to teleport to your mine!").build());
            this.setAction(slot, event -> {
                playerMine.teleport();
                return true;
            });
        }

    }

    private void addUpgradeBoosterItem(){
        int[] slots = {6, 7, 8, 15, 16, 17, 24, 25, 26, 33, 34, 35, 42, 43, 44, 51, 52, 53};
        int cost = MineConf.get().getMineBoosterCost();
        int maxLevel = MineConf.get().getMineBoosterMax();

        boolean maxed = playerMine.getBooster() >= maxLevel;
        ItemStack item = maxed ? new ItemBuilder(Material.PAPER).displayname("§aUpgrade Booster").modelData(10006)
                .lore("§7Upgrade your mine booster by 1x", "", "§cMax Level Reached").build() :
                new ItemBuilder(Material.PAPER).displayname("§aUpgrade Booster").modelData(10006)
                        .lore("§7Upgrade your mine booster by 1x", "§cCost: §f" + cost, "§7", "§cCurrent Multiplier: " + playerMine.getBooster()).build();
        for(int slot: slots) {
            getInventory().setItem(slot, item);
            this.setAction(slot, event -> {
                if(maxed) {
                    event.getWhoClicked().sendMessage("§cYou have reached the max level for your mine booster");
                    return true;
                }
                ProfilePlayer profile = ProfilePlayer.get(player.getUniqueId());
                MitchCurrency currency = profile.getCurrency("credits");
                if (currency.getAmount() >= cost) {
                    currency.take(cost);
                    playerMine.addBooster(1);
                    addUpgradeBoosterItem();
                } else {
                    player.sendMessage("§cYou do not have enough credits to upgrade your mine booster");
                }
                return true;
            });
        }
    }

    private void addResetItem(){
        int[] slots = {3, 4 ,5, 12, 13, 14, 21, 22, 23, 30, 31, 32, 39, 40, 41, 48, 49, 50};
        for(int slot: slots) {
            getInventory().setItem(slot, new ItemBuilder(Material.PAPER).displayname("§aReset Mine").modelData(10006)
                    .lore("§cReset your mine").build());
            this.setAction(slot, event -> {
                playerMine.reset();
                return true;
            });
        }
    }

    private void init() {
        addMineInformationItem();
        addUpgradeBoosterItem();
        addResetItem();
    }

    public void open() {
        player.openInventory(getInventory());
    }
}
