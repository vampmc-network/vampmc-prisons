package me.reklessmitch.mitchprisonscore.colls;

import com.massivecraft.massivecore.store.Coll;
import me.reklessmitch.mitchprisonscore.mitchbazaar.config.BazaarConf;

public class BazaarConfColl extends Coll<BazaarConf> {

    private static BazaarConfColl i = new BazaarConfColl();
    public static BazaarConfColl get(){return i;}

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        if(!active){
            return;
        }
        BazaarConf.i = this.get("insane", true);
    }
}
