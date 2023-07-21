package me.reklessmitch.mitchprisonscore.mitchmines.configs;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import lombok.Getter;
import me.reklessmitch.mitchprisonscore.mitchmines.utils.SerLoc;
import org.bukkit.Material;

import java.util.Map;
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
    private int maxMineRank = 100;
    private Map<Integer, Integer> mineRankLevels = Map.of(
            1, 1000,
            2, 5000,
            3, 10000,
            4, 25000,
            5, 50000
    );
    private Set<Material> blockMap = Set.of(Material.STONE);

}
