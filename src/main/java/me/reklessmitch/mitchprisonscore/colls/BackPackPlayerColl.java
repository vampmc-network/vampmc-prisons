package me.reklessmitch.mitchprisonscore.colls;

import com.massivecraft.massivecore.store.SenderColl;
import me.reklessmitch.mitchprisonscore.mitchbackpack.config.BackpackPlayer;

public class BackPackPlayerColl extends SenderColl<BackpackPlayer> {

    private static final BackPackPlayerColl i = new BackPackPlayerColl();
    public static BackPackPlayerColl get() { return i; }


}
