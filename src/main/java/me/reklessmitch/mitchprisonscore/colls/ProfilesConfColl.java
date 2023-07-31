package me.reklessmitch.mitchprisonscore.colls;

import com.massivecraft.massivecore.MassiveCore;
import com.massivecraft.massivecore.store.Coll;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilesConf;

public class ProfilesConfColl extends Coll<ProfilesConf> {

    private static ProfilesConfColl i = new ProfilesConfColl();

    public static ProfilesConfColl get() {
        return i;
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        if (!active) return;
        ProfilesConf.i = this.get(MassiveCore.INSTANCE, true);
    }
}
