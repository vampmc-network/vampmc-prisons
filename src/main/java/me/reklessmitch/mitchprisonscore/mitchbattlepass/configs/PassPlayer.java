package me.reklessmitch.mitchprisonscore.mitchbattlepass.configs;

import com.massivecraft.massivecore.store.SenderEntity;
import lombok.Getter;
import lombok.Setter;
import me.reklessmitch.mitchprisonscore.mitchbattlepass.object.Reward;
import me.reklessmitch.mitchprisonscore.colls.PassPlayerColl;
import me.reklessmitch.mitchprisonscore.utils.LangConf;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Getter
public class PassPlayer extends SenderEntity<PassPlayer> {

    public static PassPlayer get(Object oid) {
        return PassPlayerColl.get().get(oid);
    }

    @Override
    public PassPlayer load(@NotNull PassPlayer that) {
        super.load(that);
        return this;
    }

    private int level = 0;
    private int lastClaimedLevel = 0;
    @Setter private boolean premium = false;


    private void claimRewards(Map<Integer, List<Reward>> rewards) {
        TreeMap<Integer, List<Reward>> sortedMap = new TreeMap<>(rewards);
        SortedMap<Integer, List<Reward>> subMap = sortedMap.subMap(lastClaimedLevel + 1, level + 1);
        if(subMap.isEmpty()) {
            getPlayer().sendMessage(LangConf.get().getBattlePassNoRewards());
        }else{
            getPlayer().sendMessage(LangConf.get().getBattlePassClaimed());
            subMap.forEach((l, r) -> r.forEach(reward -> reward.getCommands().forEach(
                    command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", getPlayer().getName())))));
            lastClaimedLevel = level;
            changed();
        }
    }
    public void claimFreeRewards() {
        claimRewards(PassConf.get().getFreeRewards());
    }

    public void claimAllRewards() {
        claimPaidRewards();
        claimFreeRewards();
    }

    public void claimPaidRewards() {
        claimRewards(PassConf.get().getPaidRewards());
    }

    public void addLevel() {
        level++;
        changed();
    }
}
