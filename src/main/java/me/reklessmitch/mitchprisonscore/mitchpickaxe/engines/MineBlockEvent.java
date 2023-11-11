package me.reklessmitch.mitchprisonscore.mitchpickaxe.engines;

import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.util.MUtil;
import com.sk89q.worldedit.math.BlockVector3;
import me.clip.placeholderapi.PlaceholderAPI;
import me.reklessmitch.mitchprisonscore.MitchPrisonsCore;
import me.reklessmitch.mitchprisonscore.mitchboosters.configs.BoosterPlayer;
import me.reklessmitch.mitchprisonscore.mitchboosters.objects.Booster;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.PlayerMine;
import me.reklessmitch.mitchprisonscore.mitchmines.utils.BlockInPmineBrokeEvent;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PetPlayer;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.Pet;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PetType;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PickaxeConf;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.enchants.Enchant;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.utils.EnchantType;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.utils.CrateReward;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class MineBlockEvent extends Engine {

    /*
    See if it is possible to have global variables for this class
     */
    private static MineBlockEvent i = new MineBlockEvent();
    public static MineBlockEvent get() { return i; }

    private double getProcChance(Pet pet, EnchantType type, int level){
        double procChance = PickaxeConf.get().getEnchantByType(type).getProcChance(level);
        if(pet.getType() == PetType.CRATE && type == EnchantType.KEY_FINDER ||
                pet.getType() == PetType.SUPPLY_DROP && type == EnchantType.SUPPLY_DROP ||
                pet.getType() == PetType.JACKHAMMER_BOOST && type == EnchantType.JACKHAMMER) {
            procChance *= pet.getPetBooster();
        }
        return procChance;

    }

    @EventHandler (priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockDamage(BlockDamageEvent event){
        Block block = event.getBlock();
        ProfilePlayer currency = ProfilePlayer.get(event.getPlayer().getUniqueId());
        if(block.getType().equals(Material.BEACON)) {
            event.getBlock().setType(Material.AIR);
            currency.getCurrency("beacon").add(1);
        }
        Bukkit.getScheduler().runTaskLater(MitchPrisonsCore.get(),
                () -> event.getPlayer().sendBlockChange(block.getLocation(), block.getType(), block.getData()), 3);
    }


    @EventHandler(ignoreCancelled = true)
    public void mineBlock(BlockInPmineBrokeEvent e) {
        PetPlayer petPlayer = PetPlayer.get(e.getPlayer().getUniqueId());
        PPickaxe pickaxe = PPickaxe.get(e.getPlayer().getUniqueId());
        Pet pet = petPlayer.getPet(petPlayer.getActivePet());
        ProfilePlayer currency = ProfilePlayer.get(e.getPlayer().getUniqueId());
        addRawBlock(pet, currency, e.getPlayerMine(), pickaxe);

        pickaxe.getEnchants().forEach((type, level) -> {
            if(!pickaxe.getEnchantToggle().get(type)) return;
            double procChance = getProcChance(pet, type, level);
            if(type == EnchantType.NUKE){
                procChance *= 1 + pickaxe.getEnchantPrestiges().get(type);
            }
            if(level == 0 || procChance < MitchPrisonsCore.get().getRandom().nextDouble(1)) return;
            boolean enchantMessage = pickaxe.getEnchantMessages().get(type);
            int prestige = pickaxe.getEnchantPrestiges().get(type);
            switch (type) {
                case APOCALYPSE -> apocalypse(e, pet, currency, enchantMessage);
                case BEACON -> beacon(e, enchantMessage);
                case HASTE -> haste(e.getPlayer(), level);
                case JACKHAMMER -> jackhammer(e, pet, currency, enchantMessage, prestige);
                case KEY_FINDER -> keyFinder(e.getPlayer(), enchantMessage);
                case LOOT_FINDER -> lootFinder(e.getPlayer(), enchantMessage);
                case SCAVENGER -> scavenger(e.getPlayer(), enchantMessage);
                case SPEED -> speed(e.getPlayer(), level);
                case SUPPLY_DROP -> supplyDrop(e, enchantMessage);
                case TOKEN_POUCH -> tokenPouch(level, currency, enchantMessage);
                case NUKE -> nuke(e, pet, currency, enchantMessage);
                case EXPLOSIVE -> explosive(e, pet, currency, level, enchantMessage);
                default -> {}
            }
        });
        currency.changed();
    }

    private void addRawBlock(Pet pet, ProfilePlayer currency, PlayerMine mine, PPickaxe pickaxe) {
        pickaxe.addRawBlockBroken();
        addTokens(1, pet, currency, mine);
        addBlocksToBackpack(pickaxe.getPlayer(), 1);
    }

    private void addTokens(int blocks, Pet pet, ProfilePlayer currency, PlayerMine mine) {
        PPickaxe pickaxe = PPickaxe.get(mine.getPlayer().getUniqueId());
        int fortuneLevel = pickaxe.getEnchants().get(EnchantType.FORTUNE);
        if(fortuneLevel > 0) {
            blocks *= 1 + ((double) fortuneLevel / 1000);
        }
        int tokensToAdd = blocks;
        if(pet.getType() == PetType.TOKEN) {
            tokensToAdd = (int) (blocks * pet.getPetBooster());
        }
        tokensToAdd *= mine.getBooster();
        Booster booster = BoosterPlayer.get(mine.getPlayer().getUniqueId()).getActiveTokenBooster();
        if(booster != null){
            tokensToAdd *= booster.getMultiplier();
        }
        currency.getCurrency("token").add(tokensToAdd);
        currency.changed();
    }

    private void addBlocksToBackpack(Player player, int blocks){
        PPickaxe.get(player.getUniqueId()).addBlockBroken(blocks);
    }

    private void explosive(BlockInPmineBrokeEvent e, Pet pet, ProfilePlayer currency, int level, boolean enchantMessage) {
        PlayerMine mine = e.getPlayerMine();
        int radiusIncrease = level / PickaxeConf.get().getExplosiveLevelsPerIncrease();
        int radius = PickaxeConf.get().getExplosiveStartRadius() + radiusIncrease;
        int blocks = mine.getExplosiveBlocks(e.getBlock().getLocation(), radius);
        addTokens(blocks, pet, currency, mine);
        addBlocksToBackpack(e.getPlayer(), blocks);
        if(enchantMessage) {
            e.getPlayer().sendMessage(PickaxeConf.get().getEnchantByType(EnchantType.EXPLOSIVE).getEnchantMessage());
        }
    }

    private void nuke(BlockInPmineBrokeEvent e, Pet pet, ProfilePlayer currency, boolean enchantMessage) {
        PlayerMine mine = e.getPlayerMine();
        int blocks = (int) (mine.getVolume() - mine.getVolumeMined());
        addTokens(blocks, pet, currency, mine);
        addBlocksToBackpack(e.getPlayer(), blocks);
        mine.reset();
        if(enchantMessage) {
            e.getPlayer().sendMessage(PickaxeConf.get().getEnchantByType(EnchantType.NUKE).getEnchantMessage());
        }
    }

    private void apocalypse(BlockInPmineBrokeEvent e, Pet pet, ProfilePlayer currency, boolean enchantMessage) {
        int blocks = e.getPlayerMine().apocalypse();
        addTokens(blocks, pet, currency, e.getPlayerMine());
        addBlocksToBackpack(e.getPlayer(), blocks);
        if(enchantMessage) {
            e.getPlayer().sendMessage(PickaxeConf.get().getEnchantByType(EnchantType.APOCALYPSE).getEnchantMessage());
        }
    }

    private void beacon(BlockInPmineBrokeEvent e, boolean enchantMessage) {
        Location centerLocation = e.getBlock().getLocation();
        PPickaxe pickaxe = PPickaxe.get(e.getPlayer().getUniqueId());
        int prestigeMulti = 1 + pickaxe.getEnchantPrestiges().get(EnchantType.BEACON);
        List<Block> surroundingBlocks = new ArrayList<>();
        for (int xOffset = -5; xOffset <= 5; xOffset++) {
            for (int zOffset = -5; zOffset <= 5; zOffset++) {
                if (xOffset == 0 && zOffset == 0) continue;
                Location location = centerLocation.clone().add(xOffset, 0, zOffset);
                Block surroundingBlock = location.getBlock();
                if (surroundingBlock.getType() == Material.AIR ||
                        !e.getPlayerMine().isInMine(BlockVector3.at(surroundingBlock.getX(), surroundingBlock.getY(), surroundingBlock.getZ())))
                    continue;
                surroundingBlocks.add(surroundingBlock);
            }
        }


        int rand = MitchPrisonsCore.get().getRandom().nextInt(1, 10);
        List<Block> beaconBlocks = MUtil.randomSubset(surroundingBlocks, rand * prestigeMulti);
        beaconBlocks.forEach(block -> block.setType(Material.BEACON));
        if(enchantMessage) {
            e.getPlayer().sendMessage(PickaxeConf.get().getEnchantByType(EnchantType.BEACON).getEnchantMessage().replace(
                    "%blocks%", String.valueOf(beaconBlocks.size())));
        }
    }

    private void haste(Player player, int level) {
        if(player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) return;
        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, level));
    }

    private void jackhammer(BlockInPmineBrokeEvent e, Pet pet, ProfilePlayer currency, boolean enchantMessage, int prestige) {
        for(int y = 0; y < prestige + 1; y++) {
            int blocks = e.getPlayerMine().getBlocksOnYLayer(e.getBlock().getY() - y);
            addTokens(blocks, pet, currency, e.getPlayerMine());
            addBlocksToBackpack(e.getPlayer(), blocks);
        }
        if(enchantMessage) {
            e.getPlayer().sendMessage(PickaxeConf.get().getEnchantByType(EnchantType.JACKHAMMER).getEnchantMessage());
        }
    }

    private void keyFinder(Player player, boolean enchantMessage) {
        PPickaxe pickaxe = PPickaxe.get(player);
        String key = pickaxe.isVirtualKey() ? "virtualkey" : "key";
        int amount = 1 + pickaxe.getEnchantPrestiges().get(EnchantType.KEY_FINDER);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format("crate %s give %s Mine %s", key, player.getName(), amount));
        if(enchantMessage) {
            player.sendMessage(PickaxeConf.get().getEnchantByType(EnchantType.KEY_FINDER).getEnchantMessage());
        }
    }

    private void lootFinder(Player player, boolean enchantMessage) {
        PPickaxe pickaxe = PPickaxe.get(player);
        String key = pickaxe.isVirtualKey() ? "virtualkey" : "key";
        int amount = 1 + pickaxe.getEnchantPrestiges().get(EnchantType.KEY_FINDER);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format("crate %s give %s Loot %s", key, player.getName(), amount));
        if(enchantMessage) {
            player.sendMessage(PickaxeConf.get().getEnchantByType(EnchantType.LOOT_FINDER).getEnchantMessage());
        }
    }

    private void addLevelToEnchant(EnchantType type, PickaxeConf conf, PPickaxe pickaxe){
        if(pickaxe.getEnchants().get(type) < conf.getEnchantByType(type).getMaxLevel()) {
            pickaxe.getEnchants().replace(type, pickaxe.getEnchants().get(type) + 1);
        }
    }

    private void scavenger(Player player, boolean enchantMessage) {
        PPickaxe pickaxe = PPickaxe.get(player.getUniqueId());
        PickaxeConf conf = PickaxeConf.get();

        addLevelToEnchant(EnchantType.EFFICIENCY, conf, pickaxe);
        addLevelToEnchant(EnchantType.FORTUNE, conf, pickaxe);
        int prestigeLevel = pickaxe.getEnchants().get(EnchantType.SCAVENGER);
        if (prestigeLevel == 1) {
            addLevelToEnchant(EnchantType.JACKHAMMER, conf, pickaxe);
        } else if (prestigeLevel == 2) {
            addLevelToEnchant(EnchantType.NUKE, conf, pickaxe);
        }
        pickaxe.changed();
        pickaxe.updatePickaxe();
        if(enchantMessage) {
            player.sendMessage(PickaxeConf.get().getEnchantByType(EnchantType.SCAVENGER).getEnchantMessage());
        }
    }

    private void speed(Player player, int level) {
        if(player.hasPotionEffect(PotionEffectType.SPEED)) return;
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, level));
    }

    private void supplyDrop(BlockInPmineBrokeEvent e, boolean enchantMessage) {
        Player player = e.getPlayer();
        PPickaxe pickaxe = PPickaxe.get(player);
        int prestigeBonus = 1 + pickaxe.getEnchantPrestiges().get(EnchantType.SUPPLY_DROP);
        for(int pb = 0; pb < prestigeBonus; pb++){
            CrateReward reward = PickaxeConf.get().getReward();
            reward.getCommands(player).forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                    PlaceholderAPI.setPlaceholders(player, command)));
            player.sendMessage(PlaceholderAPI.setPlaceholders(player, reward.getMessage(player)));
        }
        player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_SHOOT, 1, 1);
        if(enchantMessage) {
            player.sendMessage(PickaxeConf.get().getEnchantByType(EnchantType.SUPPLY_DROP).getEnchantMessage());
        }
    }

    private void tokenPouch(int level, ProfilePlayer currency, boolean enchantMessage) {
        PickaxeConf conf = PickaxeConf.get();
        int baseAmount = conf.getTokenPouchBaseAmount();
        int prestigeMulti = 1 + PPickaxe.get(currency.getPlayer()).getEnchants().get(EnchantType.TOKEN_POUCH);
        double petMultiplier = PetPlayer.get(currency.getPlayer()).getPet(PetType.TOKEN).getPetBooster();
        BigInteger tokensToGive = BigInteger.valueOf((long) (baseAmount +
                ((long) conf.getTokenPouchIncreasePerLevel() * level) * petMultiplier * prestigeMulti));

        currency.getCurrency("token").add(tokensToGive);
        if(enchantMessage) {
            currency.getPlayer().sendMessage(PickaxeConf.get().getEnchantByType(EnchantType.TOKEN_POUCH).getEnchantMessage().replace(
                    "%tokens%", String.valueOf(tokensToGive)));
        }
    }
}
