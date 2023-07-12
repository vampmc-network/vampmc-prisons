package me.reklessmitch.mitchprisonscore.mitchpickaxe.colls;

import com.massivecraft.massivecore.store.SenderColl;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;

public class PPickaxeColl extends SenderColl<PPickaxe> {

    private static final PPickaxeColl i = new PPickaxeColl();
    public static PPickaxeColl get() { return i; }

}
