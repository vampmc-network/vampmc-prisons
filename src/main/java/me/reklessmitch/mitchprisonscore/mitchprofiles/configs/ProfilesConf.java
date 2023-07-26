package me.reklessmitch.mitchprisonscore.mitchprofiles.configs;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import lombok.Getter;
import me.reklessmitch.mitchprisonscore.mitchprofiles.object.ShopItem;
import org.bukkit.Material;

import java.util.List;

@Getter
@EditorName("config")
public class ProfilesConf extends Entity<ProfilesConf> {
    protected static transient ProfilesConf i;
    public static ProfilesConf get() { return i; }

    private List<String> currencyList = List.of("token", "beacon", "money", "credits");
    private List<String> joinMessages = List.of("%player% joined", "%player% is here");

    List<ShopItem> creditShop = List.of(new ShopItem(1, 10, Material.DIAMOND, "§aDiamond",
            "§aA diamond", List.of("eco give %player% 1000"), List.of("§aCost: %cost%")));

}
