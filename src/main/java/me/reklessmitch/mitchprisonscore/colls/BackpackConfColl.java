package me.reklessmitch.mitchprisonscore.colls;

import com.massivecraft.massivecore.store.Coll;
import me.reklessmitch.mitchprisonscore.mitchbackpack.config.BackpackConf;

public class BackpackConfColl extends Coll<BackpackConf> {

    private static final BackpackConfColl i = new BackpackConfColl();
    public static BackpackConfColl get() { return i; }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        if(!active){
            return;
        }
        BackpackConf.i = this.get("insane", true);
    }
}
