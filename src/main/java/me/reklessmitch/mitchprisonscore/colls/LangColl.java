package me.reklessmitch.mitchprisonscore.colls;

import com.massivecraft.massivecore.store.Coll;
import me.reklessmitch.mitchprisonscore.utils.LangConf;

public class LangColl extends Coll<LangConf> {

    private static final LangColl i = new LangColl();
    public static LangColl get() { return i; }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        if(!active){
            return;
        }
        LangConf.i = this.get("insane", true);
    }
}