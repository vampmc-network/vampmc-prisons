package me.reklessmitch.mitchprisonscore.colls;

import com.massivecraft.massivecore.store.SenderColl;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.PlayerMine;

public class PlayerMineColl extends SenderColl<PlayerMine> {

    private static final PlayerMineColl i = new PlayerMineColl();
    public static PlayerMineColl get() { return i; }

}
