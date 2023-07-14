package me.reklessmitch.mitchprisonscore.colls;

import com.massivecraft.massivecore.store.SenderColl;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PPlayer;

public class PPlayerColl extends SenderColl<PPlayer> {
    private static final PPlayerColl i = new PPlayerColl();
    public static PPlayerColl get() { return i; }
}
