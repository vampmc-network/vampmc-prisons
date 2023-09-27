package me.reklessmitch.mitchprisonscore.mitchboosters.objects;

import com.massivecraft.massivecore.util.ItemBuilder;
import lombok.Getter;
import lombok.Setter;
import me.reklessmitch.mitchprisonscore.MitchPrisonsCore;
import me.reklessmitch.mitchprisonscore.mitchboosters.utils.BoosterType;
import me.reklessmitch.mitchprisonscore.mitchboosters.utils.TimeFormat;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PickaxeConf;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

@Getter
@Setter
public class Booster{

    private long timeInSeconds;
    private double multiplier;
    private BoosterType type;
    private boolean active = false;

    public Booster(BoosterType type, double multiplier, long timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
        this.multiplier = multiplier;
        this.type = type;
    }

    public ItemStack getBoosterItem(){
        ItemStack i =  new ItemBuilder(PickaxeConf.get().getBoosterItems().get(type)).displayname("§a" + type.name() + " Booster")
            .lore(List.of("§7Multiplier: " + multiplier, "§7Time: " + TimeFormat.formatSeconds((int) timeInSeconds)))
            .glow().build();
        ItemMeta meta = i.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(MitchPrisonsCore.get().getTypeKey(), PersistentDataType.STRING, type.toString());
        container.set(MitchPrisonsCore.get().getMultiKey(), PersistentDataType.DOUBLE, multiplier);
        container.set(MitchPrisonsCore.get().getDurationKey(), PersistentDataType.LONG, timeInSeconds);
        i.setItemMeta(meta);
        return i;
    }
}
