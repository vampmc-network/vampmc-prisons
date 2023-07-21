package me.reklessmitch.mitchprisonscore.mitchboosters.configs;

import com.massivecraft.massivecore.store.SenderEntity;
import lombok.Getter;
import lombok.Setter;
import me.reklessmitch.mitchprisonscore.colls.BoosterPlayerColl;
import me.reklessmitch.mitchprisonscore.mitchboosters.objects.Booster;
import me.reklessmitch.mitchprisonscore.mitchboosters.utils.BoosterType;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Setter @Getter
public class BoosterPlayer extends SenderEntity<BoosterPlayer> {

    Booster activeTokenBooster = null;
    Booster activeMoneyBooster = null;
    Booster activeBeaconBooster = null;
    List<Booster> boosters = new ArrayList<>();

    public static BoosterPlayer get(Object oid) {
        return BoosterPlayerColl.get().get(oid);
    }

    @Override
    public BoosterPlayer load(@NotNull BoosterPlayer that) {
        super.load(that);
        return this;
    }

    public void combineBoosters() {
        int changes = 0;
        Map<BoosterType, Map<Double, Long>> multipliersToTime = new EnumMap<>(BoosterType.class);
        for(Booster booster : boosters){
            if(multipliersToTime.containsKey(booster.getType())){
                Map<Double, Long> multipliers = multipliersToTime.get(booster.getType());
                if(multipliers.containsKey(booster.getMultiplier())){
                    multipliers.replace(booster.getMultiplier(), multipliers.get(booster.getMultiplier()) + booster.getTimeInSeconds());
                    changes++;
                }else{
                    multipliers.put(booster.getMultiplier(), booster.getTimeInSeconds());
                }
            }else{
                Map<Double, Long> multipliers = new HashMap<>();
                multipliers.put(booster.getMultiplier(), booster.getTimeInSeconds());
                multipliersToTime.put(booster.getType(), multipliers);
            }
        }
        if(changes > 0){
            boosters.clear();
            multipliersToTime.forEach((type, multipliers) ->
                    multipliers.forEach((multiplier, time) ->
                            boosters.add(new Booster(type, multiplier, time))));
            getPlayer().sendMessage("§aYour boosters have been combined!");
        }else{
            getPlayer().sendMessage("§cYou have no boosters to combine!");
        }
    }
}