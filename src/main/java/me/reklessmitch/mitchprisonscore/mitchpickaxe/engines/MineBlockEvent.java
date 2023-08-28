package me.reklessmitch.mitchprisonscore.mitchpickaxe.engines;

import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.util.MUtil;
import com.sk89q.worldedit.math.BlockVector3;
import me.reklessmitch.mitchprisonscore.MitchPrisonsCore;
import me.reklessmitch.mitchprisonscore.mitchboosters.configs.BoosterPlayer;
import me.reklessmitch.mitchprisonscore.mitchboosters.objects.Booster;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.PlayerMine;
import me.reklessmitch.mitchprisonscore.mitchmines.utils.BlockInPmineBrokeEvent;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PPlayer;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.Pet;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PetType;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PickaxeConf;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.utils.EnchantType;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
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

import java.util.ArrayList;
import java.util.List;

public class MineBlockEvent extends Engine {

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
            currency.getCurrency("beacon").add(1);
            event.getPlayer().sendBlockChange(block.getLocation(), Material.AIR, (byte) 0);
            return;
        }
        Bukkit.getScheduler().runTaskLater(MitchPrisonsCore.get(),
                () -> event.getPlayer().sendBlockChange(block.getLocation(), block.getType(), block.getData()), 3);
    }


    @EventHandler(ignoreCancelled = true)
    public void mineBlock(BlockInPmineBrokeEvent e) {
        PPlayer pPlayer = PPlayer.get(e.getPlayer().getUniqueId());
        PPickaxe pickaxe = PPickaxe.get(e.getPlayer().getUniqueId());
        Pet pet = pPlayer.getPet(pPlayer.getActivePet());
        ProfilePlayer currency = ProfilePlayer.get(e.getPlayer().getUniqueId());
        addRawBlock(pet, currency, e.getPlayerMine(), pickaxe);


        pickaxe.getEnchants().forEach((type, level) -> {
            if(!pickaxe.getEnchantToggle().get(type)) return;
            double procChance = getProcChance(pet, type, level);
            if(level == 0 || procChance < MitchPrisonsCore.get().getRandom().nextDouble(1)) return;
            switch (type) {
                case APOCALYPSE -> apocalypse(e, pet, currency);
                case BEACON -> beacon(e);
                case HASTE -> haste(e.getPlayer(), level);
                case JACKHAMMER -> jackhammer(e, pet, currency);
                case KEY_FINDER -> keyFinder(e.getPlayer());
                case LOOT_FINDER -> lootFinder(e.getPlayer());
                case SCAVENGER -> scavenger(e.getPlayer());
                case SPEED -> speed(e.getPlayer(), level);
                case SUPPLY_DROP -> supplyDrop(e);
                case TOKEN_POUCH -> tokenPouch(level, currency);
                case NUKE -> nuke(e, pet, currency);
                case EXPLOSIVE -> explosive(e, pet, currency, level);
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

    private void explosive(BlockInPmineBrokeEvent e, Pet pet, ProfilePlayer currency, int level) {
        PlayerMine mine = e.getPlayerMine();
        int radiusIncrease = level / PickaxeConf.get().getExplosiveLevelsPerIncrease();
        int radius = PickaxeConf.get().getExplosiveStartRadius() + radiusIncrease;
        int blocks = mine.getExplosiveBlocks(e.getBlock().getLocation(), radius);
        addTokens(blocks, pet, currency, mine);
        addBlocksToBackpack(e.getPlayer(), blocks);
        if(PPickaxe.get(e.getPlayer()).getEnchantMessages().get(EnchantType.EXPLOSIVE)) {
            e.getPlayer().sendMessage("§6§lExplosive has been activated");
        }
    }

    private void nuke(BlockInPmineBrokeEvent e, Pet pet, ProfilePlayer currency) {
        PlayerMine mine = e.getPlayerMine();
        int blocks = (int) (mine.getVolume() - mine.getVolumeMined());
        addTokens(blocks, pet, currency, mine);
        addBlocksToBackpack(e.getPlayer(), blocks);
        mine.reset();
        if(PPickaxe.get(e.getPlayer()).getEnchantMessages().get(EnchantType.NUKE)) {
            e.getPlayer().sendMessage("§6§lNuke has been activated");
        }
    }

    private void apocalypse(BlockInPmineBrokeEvent e, Pet pet, ProfilePlayer currency) {
        int blocks = e.getPlayerMine().apocalypse();
        addTokens(blocks, pet, currency, e.getPlayerMine());
        addBlocksToBackpack(e.getPlayer(), blocks);
        if(PPickaxe.get(e.getPlayer()).getEnchantMessages().get(EnchantType.APOCALYPSE)) {
            e.getPlayer().sendMessage("§6§lApocalypse has been activated");
        }
    }

    private void beacon(BlockInPmineBrokeEvent e) {
        Location centerLocation = e.getBlock().getLocation();
        List<Block> surroundingBlocks = new ArrayList<>();
        for (int xOffset = -3; xOffset <= 3; xOffset++) {
            for (int zOffset = -3; zOffset <= 3; zOffset++) {
                if (xOffset == 0 && zOffset == 0) continue;
                Location location = centerLocation.clone().add(xOffset, 0, zOffset);
                Block surroundingBlock = location.getBlock();
                if (surroundingBlock.getType() == Material.AIR ||
                        !e.getPlayerMine().isInMine(BlockVector3.at(surroundingBlock.getX(), surroundingBlock.getY(), surroundingBlock.getZ())))
                    continue;
                surroundingBlocks.add(surroundingBlock);
            }
        }

        int rand = MitchPrisonsCore.get().getRandom().nextInt(1, 35);
        List<Block> beaconBlocks = MUtil.randomSubset(surroundingBlocks, rand);
        beaconBlocks.forEach(block -> block.setType(Material.BEACON));
        if (PPickaxe.get(e.getPlayer()).getEnchantMessages().get(EnchantType.BEACON)) {
            e.getPlayer().sendMessage("§6§l" + e.getPlayer().getName() + " §7has found a §6§l" + rand + "x Beacons §7from Beacon Finder!");
        }
    }

    private void haste(Player player, int level) {
        if(player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) return;
        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, level));
    }

    private void jackhammer(BlockInPmineBrokeEvent e, Pet pet, ProfilePlayer currency) {
        int blocks = e.getPlayerMine().getBlocksOnYLayer(e.getBlock().getY());
        addTokens(blocks, pet, currency, e.getPlayerMine());
        addBlocksToBackpack(e.getPlayer(), blocks);
        if(PPickaxe.get(e.getPlayer()).getEnchantMessages().get(EnchantType.JACKHAMMER)) {
            e.getPlayer().sendMessage("§6§lJackhammer has been activated");
        }
    }

    private void keyFinder(Player player) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "crate virtualkey give " + player.getName() + "Mine 1");
        if(PPickaxe.get(player).getEnchantMessages().get(EnchantType.KEY_FINDER)) {
            player.sendMessage("§6§lFound §6§l1x Loot Key from loot finder!");
        }
    }

    private void lootFinder(Player player) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "crate virtualkey give " + player.getName() + "Loot 1");
        if(PPickaxe.get(player).getEnchantMessages().get(EnchantType.LOOT_FINDER)) {
            player.sendMessage("§6§lFound §6§l1x Loot Key from loot finder!");
        }
    }

    private void scavenger(Player player) {
        PPickaxe pickaxe = PPickaxe.get(player.getUniqueId());
        PickaxeConf conf = PickaxeConf.get();
        if(!pickaxe.getEnchants().get(EnchantType.EFFICIENCY).equals(conf.getEnchantByType(EnchantType.EFFICIENCY).getMaxLevel())) {
            pickaxe.getEnchants().replace(EnchantType.EFFICIENCY, pickaxe.getEnchants().get(EnchantType.EFFICIENCY) + 1);
        }
        if(!pickaxe.getEnchants().get(EnchantType.FORTUNE).equals(conf.getEnchantByType(EnchantType.FORTUNE).getMaxLevel())) {
            pickaxe.getEnchants().replace(EnchantType.FORTUNE, pickaxe.getEnchants().get(EnchantType.FORTUNE) + 1);
        }
        pickaxe.changed();
        pickaxe.updatePickaxe();
        if(pickaxe.getEnchantMessages().get(EnchantType.SCAVENGER)) {
            player.sendMessage("§6§lFound §6§lScavenger has activated, fortune and efficiency have been upgraded!");
        }
    }

    private void speed(Player player, int level) {
        if(player.hasPotionEffect(PotionEffectType.SPEED)) return;
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, level));
    }

    private void supplyDrop(BlockInPmineBrokeEvent e) {
        Player player = e.getPlayer();
        e.getBlock().setType(Material.ENDER_CHEST);
        e.getPlayer().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_SHOOT, 1, 1);
        if(PPickaxe.get(player.getUniqueId()).getEnchantMessages().get(EnchantType.SUPPLY_DROP)) {
            e.getPlayer().sendMessage("§6§lFound a §6§lSupply Drop!");
        }
    }

    private void tokenPouch(int level, ProfilePlayer currency) {
        long tokensToGive = PickaxeConf.get().getTokenPouchBaseAmount() +
                ((long) PickaxeConf.get().getTokenPouchIncreasePerLevel() * level);
        currency.getCurrency("token").add(tokensToGive);
        if(PPickaxe.get(currency.getId()).getEnchantMessages().get(EnchantType.TOKEN_POUCH)) {
            currency.getPlayer().sendMessage("§6§lToken Pouch§7: §6§l" + tokensToGive + " Tokens§7!");
        }
    }
}
