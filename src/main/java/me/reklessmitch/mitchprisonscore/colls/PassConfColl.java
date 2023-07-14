package me.reklessmitch.mitchprisonscore.colls;

import com.massivecraft.massivecore.store.Coll;
import me.reklessmitch.mitchprisonscore.battlepass.configs.PassConf;

public class PassConfColl extends Coll<PassConf> {

    private static final PassConfColl i = new PassConfColl();
    public static PassConfColl get() { return i; }
}
