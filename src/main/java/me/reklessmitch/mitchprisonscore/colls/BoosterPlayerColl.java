package me.reklessmitch.mitchprisonscore.colls;


import com.massivecraft.massivecore.store.SenderColl;
import me.reklessmitch.mitchprisonscore.mitchboosters.configs.BoosterPlayer;

public class BoosterPlayerColl extends SenderColl<BoosterPlayer> {

    private static final BoosterPlayerColl i = new BoosterPlayerColl();
    public static BoosterPlayerColl get() { return i; }


}
