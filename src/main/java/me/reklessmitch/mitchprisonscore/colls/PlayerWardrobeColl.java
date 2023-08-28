package me.reklessmitch.mitchprisonscore.colls;

import com.massivecraft.massivecore.store.SenderColl;
import me.reklessmitch.mitchprisonscore.mitchprofiles.holograms.PlayerWardrobe;

public class PlayerWardrobeColl extends SenderColl<PlayerWardrobe> {
    private static final PlayerWardrobeColl i = new PlayerWardrobeColl();
    public static PlayerWardrobeColl get() { return i; }

}
