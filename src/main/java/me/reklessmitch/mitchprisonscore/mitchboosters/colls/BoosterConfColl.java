package me.reklessmitch.mitchprisonscore.mitchboosters.colls;

import com.massivecraft.massivecore.store.Coll;
import me.reklessmitch.mitchprisonscore.mitchboosters.configs.BoosterConf;

public class BoosterConfColl extends Coll<BoosterConf> {


    private static final BoosterConfColl i = new BoosterConfColl();
    public static BoosterConfColl get() { return i; }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        if(!active){
            return;
        }
        BoosterConf.i = this.get("insane", true);
    }

}
