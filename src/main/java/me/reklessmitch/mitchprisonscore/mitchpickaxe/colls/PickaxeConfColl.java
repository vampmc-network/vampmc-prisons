package me.reklessmitch.mitchprisonscore.mitchpickaxe.colls;

import com.massivecraft.massivecore.store.Coll;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PickaxeConf;

public class PickaxeConfColl extends Coll<PickaxeConf> {

    private static final PickaxeConfColl i = new PickaxeConfColl();
    public static PickaxeConfColl get() { return i; }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        if(!active){
            return;
        }
        PickaxeConf.i = this.get("insane", true);
    }
}
