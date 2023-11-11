package me.reklessmitch.mitchprisonscore;

import com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
import com.massivecraft.massivecore.MassivePlugin;
import lombok.Getter;
import me.reklessmitch.mitchprisonscore.colls.*;
import me.reklessmitch.mitchprisonscore.mitchbackpack.cmds.base.CmdSell;
import me.reklessmitch.mitchprisonscore.mitchbackpack.engine.BlocksToBackpack;
import me.reklessmitch.mitchprisonscore.mitchbackpack.engine.PlayerInteract;
import me.reklessmitch.mitchprisonscore.mitchbackpack.placeholders.BackpackPlaceholders;
import me.reklessmitch.mitchprisonscore.mitchbattlepass.cmds.CmdPass;
import me.reklessmitch.mitchprisonscore.mitchbattlepass.engines.UpgradeEvent;
import me.reklessmitch.mitchprisonscore.mitchbazaar.cmd.CmdBazaar;
import me.reklessmitch.mitchprisonscore.mitchboosters.cmds.booster.CmdBooster;
import me.reklessmitch.mitchprisonscore.mitchboosters.engines.BoosterInteract;
import me.reklessmitch.mitchprisonscore.mitchcells.cmds.cellcmds.CmdCell;
import me.reklessmitch.mitchprisonscore.mitchmines.cmds.def.CmdMine;
import me.reklessmitch.mitchprisonscore.mitchmines.engine.MineEvents;
import me.reklessmitch.mitchprisonscore.mitchmines.placeholders.MinePlaceholders;
import me.reklessmitch.mitchprisonscore.mitchmines.utils.PMineWorldGen;
import me.reklessmitch.mitchprisonscore.mitchpets.cmd.CmdPet;
import me.reklessmitch.mitchprisonscore.mitchpets.placeholders.PetPlaceholders;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.cmds.pickaxe.CmdAutoRankup;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.cmds.pickaxe.CmdBlocks;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.cmds.pickaxe.CmdToggles;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.cmds.pickaxe.CmdUpgradeGUI;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.engines.MineBlockEvent;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.engines.PickaxeMovement;
import me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.currency.*;
import me.reklessmitch.mitchprisonscore.mitchprofiles.cmds.joinmessage.CmdChangeJoinMessage;
import me.reklessmitch.mitchprisonscore.colls.ProfilePlayerColl;
import me.reklessmitch.mitchprisonscore.colls.ProfilesConfColl;
import me.reklessmitch.mitchprisonscore.mitchprofiles.engines.PlayerEvents;
import me.reklessmitch.mitchprisonscore.mitchprofiles.placeholders.CurrencyPlaceholders;
import me.reklessmitch.mitchprisonscore.mitchprofiles.placeholders.ProfilePlaceholders;
import me.reklessmitch.mitchprisonscore.mitchrankup.cmds.*;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


@Getter
public final class MitchPrisonsCore extends MassivePlugin {

    private static MitchPrisonsCore i;
    public static MitchPrisonsCore get() { return i; }

    public MitchPrisonsCore() {
        i = this;
        this.versionSynchronized = false;
    }

    private void createPrivateMineWorld(){
        new PMineWorldGen("privatemines").createWorld();
    }
    private Map<UUID, ArmorStand> playerWardrobes = new HashMap<>();

    // Booster Keys
    private final NamespacedKey typeKey = new NamespacedKey(this, "boosterType");
    private final NamespacedKey multiKey = new NamespacedKey(this, "boosterMultiplier");
    private final NamespacedKey durationKey = new NamespacedKey(this, "boosterDuration");
    private final NamespacedKey noMove = new NamespacedKey(this, "noMove");


    private final DecimalFormat decimalFormat = new DecimalFormat("#.##");
    private WorldBorderApi worldBorderApi;
    private final Random random = new SecureRandom();

    @Override
    public void onEnableInner() {
        i = this;
        createPrivateMineWorld();
        this.activate(
                // --- Collections ---
                // Lang
                LangColl.class,
                // Backpack
                BackpackConfColl.class, BackPackPlayerColl.class,
                // BattlePass
                PassPlayerColl.class, PassConfColl.class,
                // Boosters
                BoosterPlayerColl.class,
                // Cells
                CellColls.class,
                // Mines
                MineConfColl.class, PlayerMineColl.class,
                // Pets
                PetConfColl.class, PPlayerColl.class,
                // Pickaxe
                PickaxeConfColl.class, PPickaxeColl.class,
                // Profiles
                ProfilesConfColl.class, ProfilePlayerColl.class, //PlayerWardrobeColl.class,
                // Bazaar
                BazaarConfColl.class,
                // Rankup
                RankupConfColl.class,

                // --- Commands ---
                // Backpack
                CmdSell.class,
                // BattlePass
                CmdPass.class,
                // Boosters
                CmdBooster.class,
                // Cells
                CmdCell.class,
                // Mines
                CmdMine.class,
                // Pets
                CmdPet.class,
                // Pickaxe
                CmdUpgradeGUI.class, CmdToggles.class, CmdBlocks.class, CmdAutoRankup.class,
                // Profiles
                CmdChangeJoinMessage.class, CmdCurrency.class, CmdBal.class,
                CmdCurrencyPay.class, CmdCurrencyAddAmount.class, CmdCurrencyAddPercent.class,
                CmdCurrencyShop.class, //CmdWardrobe.class,
                // Bazaar
                CmdBazaar.class,
                // Rankup
                CmdRankup.class, CmdSetRank.class, CmdRankupMax.class, CmdRankupGUI.class, CmdRankTop.class,

                // --- Listeners ---
                // Backpack
                BlocksToBackpack.class, PlayerInteract.class,
                // Boosters
                BoosterInteract.class,
                // Cells
                // BattlePass
                UpgradeEvent.class,
                // Mines
                MineEvents.class,
                // Pets
                // Pickaxe
                MineBlockEvent.class, PickaxeMovement.class,
                // Profiles
                PlayerEvents.class



        );

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new CurrencyPlaceholders().register();
            new ProfilePlaceholders().register();
            new PetPlaceholders().register();
            new MinePlaceholders().register();
            new BackpackPlaceholders().register();
        }

        worldBorderApi = Bukkit.getServicesManager().getRegistration(WorldBorderApi.class).getProvider();

    }

    @Override
    public void onDisable() {
        i = null;
        super.onDisable();
    }
}
