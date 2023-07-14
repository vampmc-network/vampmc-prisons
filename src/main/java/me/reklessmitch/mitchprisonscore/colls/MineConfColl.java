package me.reklessmitch.mitchprisonscore.colls;

import com.massivecraft.massivecore.store.Coll;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.MineConf;

public class MineConfColl extends Coll<MineConf> {

    private static final MineConfColl i = new MineConfColl();
    public static MineConfColl get() { return i; }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        if(!active){
            return;
        }
        MineConf.i = this.get("insane", true);
    }
}

