package me.reklessmitch.mitchprisonscore.mitchpickaxe.configs;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import lombok.Getter;
import lombok.Setter;

import me.reklessmitch.mitchprisonscore.mitchpickaxe.enchants.Enchant;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.utils.EnchantType;
import org.bukkit.Bukkit;
import org.bukkit.Material;

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

    private Map<EnchantType, Enchant> setUpEnchants(){
        Bukkit.broadcastMessage("Setting up enchants");
        Map<EnchantType, Enchant> enchantList = new EnumMap<>(EnchantType.class);
        enchantList.put(EnchantType.APOCALYPSE,  new Enchant(EnchantType.APOCALYPSE, Material.ZOMBIE_HEAD, "Apocalypse", List.of("4 Zombies break to bedrock"), 0, 21, 1, 1, 0.1, 0, 0, 0));
        enchantList.put(EnchantType.BEACON, new Enchant(EnchantType.BEACON, Material.BEACON , "Beacon", List.of("Beacon"), 0, 22, 1, 1,0.1, 0, 0, 0));
        enchantList.put(EnchantType.BOOST, new Enchant(EnchantType.BOOST, Material.LAPIS_LAZULI, "Boost", List.of("FASTER"), 0, 20, 1, 1, 0.1, 0, 0, 0));
        enchantList.put(EnchantType.EFFICIENCY, new Enchant(EnchantType.EFFICIENCY, Material.DIAMOND_PICKAXE, "Efficiency", List.of("SPEED UP MAHN"), 0, 9, 1, 1, 1, 0, 0, 0));
        enchantList.put(EnchantType.FORTUNE, new Enchant(EnchantType.FORTUNE, Material.DIAMOND, "Fortune", List.of("Increases the amount of", "blocks you get from mining"), 0, 10, 1, 3, 0.1, 0.1, 0, 0));
        enchantList.put(EnchantType.GREED, new Enchant(EnchantType.GREED, Material.SKELETON_SKULL, "Greed", List.of("Greed"), 0, 18, 1, 1, 0.1, 0, 0, 0));
        enchantList.put(EnchantType.HASTE, new Enchant(EnchantType.HASTE, Material.BLAZE_POWDER, "Haste", List.of("FASTER"), 0, 11, 1, 1, 1, 0, 0, 0));
        enchantList.put(EnchantType.JACKHAMMER, new Enchant(EnchantType.JACKHAMMER, Material.ANVIL, "Jackhammer", List.of("Breaks layer"), 0, 13, 1, 1, 0.1, 0, 0, 0));
        enchantList.put(EnchantType.KEY_FINDER, new Enchant(EnchantType.KEY_FINDER, Material.TRIPWIRE_HOOK, "Key Finder",  List.of("Finds keys bozo"), 0, 14,1, 1, 0.1, 0, 0, 0));
        enchantList.put(EnchantType.LOOT_FINDER, new Enchant(EnchantType.LOOT_FINDER, Material.CHEST, "Loot Finder", List.of("Finds loot bozo"), 0, 15, 1, 1, 0.1, 0, 0, 0));
        enchantList.put(EnchantType.SCAVENGER, new Enchant(EnchantType.SCAVENGER, Material.DIAMOND_SHOVEL, "Scavenger", List.of("Get more stuff"), 0, 17, 1, 1, 0.1, 0, 0, 0));
        enchantList.put(EnchantType.SPEED, new Enchant(EnchantType.SPEED, Material.SUGAR, "Speed", List.of("SPEED"), 0, 12, 1, 1, 1, 0, 0, 0));
        enchantList.put(EnchantType.SUPPLY_DROP, new Enchant(EnchantType.SUPPLY_DROP, Material.CHEST, "Supply Drop", List.of("Get more stuff"), 0, 16, 1, 1, 0.1, 0, 0, 0));
        enchantList.put(EnchantType.TOKEN_POUCH, new Enchant(EnchantType.TOKEN_POUCH, Material.MAGMA_CREAM, "Token Pouch", List.of("Gives tokens"), 0, 19, 1, 1, 0.1, 0, 0, 0));
        enchantList.put(EnchantType.NUKE, new Enchant(EnchantType.NUKE, Material.TNT, "Nuke", List.of("Nuke"), 0, 23, 1, 1, 0.1, 0, 0, 0));
        enchantList.put(EnchantType.EXPLOSIVE, new Enchant(EnchantType.EXPLOSIVE, Material.FIREWORK_STAR, "Explosive", List.of("Explosive"), 0, 24, 1, 1, 0.1, 0, 0, 0));
        enchantList.forEach((enchantType, enchant) -> Bukkit.broadcastMessage(enchant.getClass().toString()));
        return enchantList;
    }

    public Enchant getEnchantBySlot(int slot) {
        return enchants.values().stream().filter(enchant -> enchant.getDisplayItem().
                getSlot() == slot).findFirst().orElse(null);
    }

    public Enchant getEnchantByType(EnchantType type) {
        return enchants.get(type);
    }

}
