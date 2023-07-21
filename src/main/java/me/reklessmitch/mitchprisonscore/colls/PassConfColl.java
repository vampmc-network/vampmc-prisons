package me.reklessmitch.mitchprisonscore.colls;

import com.massivecraft.massivecore.store.Coll;
import me.reklessmitch.mitchprisonscore.mitchbattlepass.configs.PassConf;

public class PassConfColl extends Coll<PassConf> {

    private static final PassConfColl i = new PassConfColl();
    public static PassConfColl get() { return i; }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        if(!active){
            return;
        }
        PassConf.i = this.get("insane", true);
    }

}
