package me.reklessmitch.mitchprisonscore.mitchprofiles.configs;

import com.massivecraft.massivecore.store.SenderColl;

public class ProfilePlayerColl extends SenderColl<ProfilePlayer>{

    private static final ProfilePlayerColl i = new ProfilePlayerColl();
    public static ProfilePlayerColl get() {
        return i;
    }

}