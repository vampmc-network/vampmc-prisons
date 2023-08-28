package me.reklessmitch.mitchprisonscore.colls;

import com.massivecraft.massivecore.store.Coll;
import me.reklessmitch.mitchprisonscore.mitchrankup.config.RankupConf;

public class RankupConfColl extends Coll<RankupConf> {
    private static final RankupConfColl i = new RankupConfColl();
    public static RankupConfColl get() { return i; }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        if(!active){
            return;
        }
        RankupConf.i = this.get("insane", true);
    }
}
