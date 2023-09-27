package me.reklessmitch.mitchprisonscore.mitchmines.configs;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import lombok.Getter;
import me.reklessmitch.mitchprisonscore.mitchmines.utils.SerLoc;
import org.bukkit.Material;

import java.util.Set;

@Getter
@EditorName("config")
public class MineConf extends Entity<MineConf> {
    public static MineConf i;
    public static MineConf get() { return i; }

    private int mineSizeRadius = 40;
    private int mineYLevel = 107;
    private SerLoc mineOffset = new SerLoc(-135, 19, -48);
    private int mineBoosterCost = 1000;
    private int mineBoosterMax = 10;
    private int maxMineSize = 100;
    private int startValue = 5000;
    private int increaseAmount = 1000;
    private double increment = 3.2;

    private Set<Material> blockMap = Set.of(Material.STONE);

    public long getNextMineLevelBlockRequirement(int level){
        return (long) (startValue + (increaseAmount * Math.pow(level - 9.0, increment)));
    }

}
