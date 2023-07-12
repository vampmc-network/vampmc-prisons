package me.reklessmitch.mitchprisonscore.mitchprofiles.configs;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import lombok.Getter;

import java.util.List;

@Getter
@EditorName("config")
public class ProfilesConf extends Entity<ProfilesConf> {
    protected static transient ProfilesConf i;
    public static ProfilesConf get() { return i; }

    private List<String> currencyList = List.of("token", "beacon", "money");
    private List<String> joinMessages = List.of("%player% joined", "%player% is here");

}
