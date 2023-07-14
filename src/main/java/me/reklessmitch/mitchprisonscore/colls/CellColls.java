package me.reklessmitch.mitchprisonscore.colls;

import com.massivecraft.massivecore.store.Coll;
import me.reklessmitch.mitchprisonscore.mitchcells.configs.CellConf;

public class CellColls extends Coll<CellConf> {

    private static final CellColls i = new CellColls();
    public static CellColls get() { return i; }
}
