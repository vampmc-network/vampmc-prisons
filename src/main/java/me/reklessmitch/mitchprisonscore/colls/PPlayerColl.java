package me.reklessmitch.mitchprisonscore.colls;

import com.massivecraft.massivecore.store.SenderColl;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PetPlayer;

public class PPlayerColl extends SenderColl<PetPlayer> {
    private static final PPlayerColl i = new PPlayerColl();
    public static PPlayerColl get() { return i; }
}
