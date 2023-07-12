package me.reklessmitch.mitchprisonscore.mitchboosters.objects;

import com.massivecraft.massivecore.util.ItemBuilder;
import lombok.Getter;
import lombok.Setter;
import me.reklessmitch.mitchprisonscore.MitchPrisonsCore;
import me.reklessmitch.mitchprisonscore.mitchboosters.configs.BoosterConf;
import me.reklessmitch.mitchprisonscore.mitchboosters.utils.BoosterType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

@Getter
@Setter
public class Booster{

    String boosterID;
    long timeInSeconds;
    double multiplier;
    BoosterType type;
    boolean active = false;

    public Booster(String id, long timeInSeconds, double multiplier, BoosterType type) {
        this.boosterID = id;
        this.timeInSeconds = timeInSeconds;
        this.multiplier = multiplier;
        this.type = type;
    }

    public ItemStack getBoosterItem(){
        return new ItemBuilder(BoosterConf.get().getBoosterItems().get(type)).displayname("§a" + type.name() + " Booster")
                .lore(List.of("§7Multiplier: " + multiplier, "§7Time: " + timeInSeconds + " seconds"))
                .withData(pdc -> pdc.set(MitchPrisonsCore.get().getKey(), PersistentDataType.STRING, boosterID))
                .glow().build();
    }
}
