package me.reklessmitch.mitchprisonscore.mitchpets.colls;

import com.massivecraft.massivecore.store.Coll;
import me.reklessmitch.mitchprisonscore.mitchpets.entity.PetConf;

public class PetConfColl extends Coll<PetConf> {
    private static final PetConfColl i = new PetConfColl();
    public static PetConfColl get() { return i; }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        if(!active){
            return;
        }
        PetConf.i = this.get("insane", true);
    }

}
