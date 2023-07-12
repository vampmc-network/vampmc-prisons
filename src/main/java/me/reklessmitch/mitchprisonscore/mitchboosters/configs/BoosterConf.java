package me.reklessmitch.mitchprisonscore.mitchboosters.configs;

import com.massivecraft.massivecore.store.Entity;
import lombok.Getter;
import me.reklessmitch.mitchprisonscore.mitchboosters.objects.Booster;
import me.reklessmitch.mitchprisonscore.mitchboosters.utils.BoosterType;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

@Getter
public class BoosterConf extends Entity<BoosterConf> {

    public static BoosterConf i = new BoosterConf();
    public static BoosterConf get() { return i; }

    long boostersMade = 0;
    Map<BoosterType, Material> boosterItems = Map.of(BoosterType.MONEY, Material.DIAMOND, BoosterType.BEACON, Material.BEACON, BoosterType.TOKEN, Material.EMERALD);
    Map<String, Booster> boosters = new HashMap<>();

}
