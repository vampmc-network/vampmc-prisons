package me.reklessmitch.mitchprisonscore.colls;

import com.massivecraft.massivecore.store.SenderColl;
import me.reklessmitch.mitchprisonscore.mitchbattlepass.configs.PassPlayer;

public class PassPlayerColl extends SenderColl<PassPlayer> {

    private static final PassPlayerColl i = new PassPlayerColl();
    public static PassPlayerColl get() { return i; }
}

