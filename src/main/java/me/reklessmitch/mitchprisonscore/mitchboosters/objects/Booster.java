package me.reklessmitch.mitchprisonscore.mitchboosters.objects;

import com.massivecraft.massivecore.util.ItemBuilder;
import lombok.Getter;
import lombok.Setter;
import me.reklessmitch.mitchprisonscore.MitchPrisonsCore;
import me.reklessmitch.mitchprisonscore.mitchboosters.configs.BoosterConf;
import me.reklessmitch.mitchprisonscore.mitchboosters.utils.BoosterType;
import me.reklessmitch.mitchprisonscore.mitchboosters.utils.TimeFormat;
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

    public Booster(BoosterType type, double multiplier, long timeInSeconds) {
        this.boosterID = "" + BoosterConf.get().getBoostersMade();
        this.timeInSeconds = timeInSeconds;
        this.multiplier = multiplier;
        this.type = type;
    }

    public ItemStack getBoosterItem(){
        return new ItemBuilder(BoosterConf.get().getBoosterItems().get(type)).displayname("§a" + type.name() + " Booster")
            .lore(List.of("§7Multiplier: " + multiplier, "§7Time: " + TimeFormat.formatSeconds((int) timeInSeconds)))
            .withData(pdc -> {
                pdc.set(MitchPrisonsCore.get().getTypeKey(), PersistentDataType.STRING, type.toString());
                pdc.set(MitchPrisonsCore.get().getMultiKey(), PersistentDataType.DOUBLE, multiplier);
                pdc.set(MitchPrisonsCore.get().getDurationKey(), PersistentDataType.LONG, timeInSeconds);
            }).glow().build();
    }
}
