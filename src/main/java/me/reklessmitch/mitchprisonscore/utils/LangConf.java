package me.reklessmitch.mitchprisonscore.utils;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import lombok.Getter;

import java.util.List;

@Getter
@EditorName("config")
public class LangConf extends Entity<LangConf> {

    public static LangConf i;
    public static LangConf get() {
        return i;
    }
    // BattlePass
    private String battlePassNoRewards = "&cYou have no rewards to claim!";
    private String battlePassClaimed = "&aYou have claimed your rewards!";


    // GUI's
    private String pickaxeGuiTitle = ":offset_-28::pickaxe:";
    private String pickaxeMainGuiTitle = ":offset_-28::mainpickaxe:";
    private String pickaxeSkinsGuiTitle = ":offset_-28::skins:";
    private String pickaxeTogglesGuiTitle = ":offset_-28::toggles:";
    private String mineGuiTitle = ":offset_-28::mine:";
    private String bazaarGuiTitle = ":offset_-28::bazaar:";
    private String petGuiTitle = ":offset_-28::pets:";
    private String boosterGuiTitle = ":offset_-28::boosters:";
    private String passGuiTitle = ":offset_-28::pass:";
    private String backpackGuiTitle = ":offset_-28::backpack:";

    // Mine
    private List<String> mineGoGUIItem = List.of("§cMine Size: §f%mitchmines_size%",
            "§cMine Block: §f%mitchmines_block%",
            "§cMine Booster: §f%mitchmines_booster%",
            "§7",
            "§aClick here to teleport to your mine!");
    private List<String> mineBoosterNotMaxed = List.of("§cMine Booster: §f%mitchmines_booster%",
            "§7",
            "§aClick here to buy a mine booster!");
    private List<String> mineBoosterMaxed = List.of("§cMine Booster: §f%mitchmines_booster%");
    private String mineMaxMineBooster = "§cYou have reached the max level for your mine booster";
    private String mineNotEnoughCredits = "§cYou do not have enough credits to upgrade your mine booster";
    private String mineReset = "§aYou have reset your mine!";
    private String mineBoosterAdded = "§aYou have added a mine booster!";

    // Rankup
    private String rankUp = "§aYou have ranked up to %mitchprofiles_rank%!";

}