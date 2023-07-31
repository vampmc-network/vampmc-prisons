package me.reklessmitch.mitchprisonscore.colls;

import com.massivecraft.massivecore.store.SenderColl;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;

public class ProfilePlayerColl extends SenderColl<ProfilePlayer>{

    private static final ProfilePlayerColl i = new ProfilePlayerColl();
    public static ProfilePlayerColl get() {
        return i;
    }

}