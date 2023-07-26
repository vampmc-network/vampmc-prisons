package me.reklessmitch.mitchprisonscore.mitchrankup.config;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import lombok.Getter;
import me.reklessmitch.mitchprisonscore.mitchrankup.object.RankupReward;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@EditorName("config")
public class RankupConf extends Entity<RankupConf> {

    public static RankupConf i;
    public static RankupConf get() { return i; }

    private int startCost = 1000;
    private int costIncrease = 5000;
    private double costMultiplier = 1.5;

    private int maxRanks = 100000;
    private int rewardsEveryXRanks = 5000;
    private Map<Integer, RankupReward> rankupRewards = setUpRankupRewards();

    private Map<Integer, RankupReward> setUpRankupRewards() {
        Map<Integer, RankupReward> rewardsMap = new HashMap<>();
        for (int l = 0; l < maxRanks; l += rewardsEveryXRanks) {
            rewardsMap.put(l, new RankupReward(
                    Material.BEACON,
                    "§aRankup Reward",
                    List.of("§aYou have reached rank " + l),
                    List.of("currency give %player% token 1000"),
                    0,
                    l,
                    0
            ));
        }
        return rewardsMap;
    }

    public int getCost(int rank){
        return (int) (startCost + (costIncrease * Math.pow(costMultiplier, rank)));
    }




}
