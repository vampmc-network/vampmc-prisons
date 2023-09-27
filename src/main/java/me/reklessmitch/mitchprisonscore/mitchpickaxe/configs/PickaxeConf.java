package me.reklessmitch.mitchprisonscore.mitchpickaxe.configs;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import lombok.Getter;
import lombok.Setter;

import me.clip.placeholderapi.PlaceholderAPI;
import me.reklessmitch.mitchprisonscore.mitchboosters.utils.BoosterType;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.enchants.Enchant;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.utils.EnchantType;
import me.reklessmitch.mitchprisonscore.utils.CrateReward;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@EditorName("config")
public class PickaxeConf extends Entity<PickaxeConf> {
    public static PickaxeConf i;
    public static PickaxeConf get() { return i; }

    Map<EnchantType, Enchant> enchants = setUpEnchants();
    private int tokenPouchBaseAmount = 100;
    private int tokenPouchIncreasePerLevel = 100;
    private int explosiveStartRadius = 2;
    private int explosiveLevelsPerIncrease = 50;
    private List<CrateReward> supplyDropRewards = List.of(new CrateReward(0.2, List.of("addc %player% token 1000"), "Tokens 1"));
    private Map<BoosterType, Material> boosterItems = Map.of(BoosterType.MONEY, Material.DIAMOND, BoosterType.BEACON, Material.BEACON, BoosterType.TOKEN, Material.EMERALD);

    public CrateReward getReward(){
        double totalChance = 0;
        for(CrateReward reward : supplyDropRewards){
            totalChance += reward.getChance();
        }
        double random = Math.random() * totalChance;
        double current = 0;
        for(CrateReward reward : supplyDropRewards){
            current += reward.getChance();
            if(random <= current){
                return reward;
            }
        }
        return null;
    }

    private Map<EnchantType, Enchant> setUpEnchants(){
        Map<EnchantType, Enchant> enchantList = new EnumMap<>(EnchantType.class);
        enchantList.put(EnchantType.APOCALYPSE,  new Enchant(EnchantType.APOCALYPSE, Material.ZOMBIE_HEAD, "Apocalypse", List.of("4 Zombies break to bedrock"), 0, 12, 1, 1, 0.1, 0, 0, 0, "APOCALYPSE"));
        enchantList.put(EnchantType.BEACON, new Enchant(EnchantType.BEACON, Material.BEACON , "Beacon", List.of("Beacon"), 0, 13, 1, 1,0.1, 0, 0, 0, "BEACON"));
        enchantList.put(EnchantType.BOOST, new Enchant(EnchantType.BOOST, Material.LAPIS_LAZULI, "Boost", List.of("FASTER"), 0, 11, 1, 1, 0.1, 0, 0, 0, "BOOST"));
        enchantList.put(EnchantType.EFFICIENCY, new Enchant(EnchantType.EFFICIENCY, Material.DIAMOND_PICKAXE, "Efficiency", List.of("SPEED UP MAHN"), 0, 0, 1, 1, 1, 0, 0, 0, "EFFICIENCY"));
        enchantList.put(EnchantType.FORTUNE, new Enchant(EnchantType.FORTUNE, Material.DIAMOND, "Fortune", List.of("Increases the amount of", "blocks you get from mining"), 0, 1, 1, 3, 0.1, 0.1, 0, 0, "FORTUNE"));
        enchantList.put(EnchantType.GREED, new Enchant(EnchantType.GREED, Material.SKELETON_SKULL, "Greed", List.of("Greed"), 0, 9, 1, 1, 0.1, 0, 0, 0, "GREED"));
        enchantList.put(EnchantType.HASTE, new Enchant(EnchantType.HASTE, Material.BLAZE_POWDER, "Haste", List.of("FASTER"), 0, 2, 1, 1, 1, 0, 0, 0, "HASTE"));
        enchantList.put(EnchantType.JACKHAMMER, new Enchant(EnchantType.JACKHAMMER, Material.ANVIL, "Jackhammer", List.of("Breaks layer"), 0, 4, 1, 1, 0.1, 0, 0, 0, "JACKHAMMER"));
        enchantList.put(EnchantType.KEY_FINDER, new Enchant(EnchantType.KEY_FINDER, Material.TRIPWIRE_HOOK, "Key Finder",  List.of("Finds keys bozo"), 0, 5,1, 1, 0.1, 0, 0, 0, "KEY_FINDER"));
        enchantList.put(EnchantType.LOOT_FINDER, new Enchant(EnchantType.LOOT_FINDER, Material.CHEST, "Loot Finder", List.of("Finds loot bozo"), 0, 6, 1, 1, 0.1, 0, 0, 0, "LOOT_FINDER"));
        enchantList.put(EnchantType.SCAVENGER, new Enchant(EnchantType.SCAVENGER, Material.DIAMOND_SHOVEL, "Scavenger", List.of("Get more stuff"), 0, 8, 1, 1, 0.1, 0, 0, 0, "SCAVENGER"));
        enchantList.put(EnchantType.SPEED, new Enchant(EnchantType.SPEED, Material.SUGAR, "Speed", List.of("SPEED"), 0, 3, 1, 1, 1, 0, 0, 0, "SPEED"));
        enchantList.put(EnchantType.SUPPLY_DROP, new Enchant(EnchantType.SUPPLY_DROP, Material.CHEST, "Supply Drop", List.of("Get more stuff"), 0, 7, 1, 1, 0.1, 0, 0, 0, "SUPPLY_DROP"));
        enchantList.put(EnchantType.TOKEN_POUCH, new Enchant(EnchantType.TOKEN_POUCH, Material.MAGMA_CREAM, "Token Pouch", List.of("Gives tokens"), 0, 12, 1, 1, 0.1, 0, 0, 0, "TOKEN_POUCH"));
        enchantList.put(EnchantType.NUKE, new Enchant(EnchantType.NUKE, Material.TNT, "Nuke", List.of("Nuke"), 0, 14, 1, 1, 0.1, 0, 0, 0, "NUKE"));
        enchantList.put(EnchantType.EXPLOSIVE, new Enchant(EnchantType.EXPLOSIVE, Material.FIREWORK_STAR, "Explosive", List.of("Explosive"), 0, 15, 1, 1, 0.1, 0, 0, 0, "EXPLOSIVE"));
        return enchantList;
    }


    public Enchant getEnchantByType(EnchantType type) {
        return enchants.get(type);
    }


    public void sendEnchantMessage(EnchantType type, Player player) {
        player.sendMessage(PlaceholderAPI.setPlaceholders(player, enchants.get(type).getEnchantMessage()));
    }
}
