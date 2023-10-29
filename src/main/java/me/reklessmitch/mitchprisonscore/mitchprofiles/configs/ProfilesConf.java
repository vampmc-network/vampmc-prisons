package me.reklessmitch.mitchprisonscore.mitchprofiles.configs;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import lombok.Getter;
import me.reklessmitch.mitchprisonscore.mitchpets.util.DisplayItem;
import me.reklessmitch.mitchprisonscore.mitchprofiles.object.ShopItem;
import org.bukkit.Material;

import java.util.List;
import java.util.Map;

@Getter
@EditorName("config")
public class ProfilesConf extends Entity<ProfilesConf> {
    public static ProfilesConf i;
    public static ProfilesConf get() { return i; }

    private List<String> currencyList = List.of("token", "beacon", "money", "credits");
    private List<String> joinMessages = List.of("%player% joined", "%player% is here");


    private final transient List<ShopItem> defaultShopItem = List.of(new ShopItem(1, 10, Material.DIAMOND, "§aRank1",
            "§aRank1", List.of("aa %player% 1"), List.of("§aCost: %cost%")));
    private final transient DisplayItem defaultDisplayItem = new DisplayItem(Material.DIAMOND, "§aRank1", List.of("aa %player% 1"), 0, 1);

    Map<String, DisplayItem> storeCategories = Map.of("ranks", defaultDisplayItem,
            "gkits", defaultDisplayItem,
            "crates", defaultDisplayItem);
    Map<String, List<ShopItem>> storeItems = Map.of("ranks", defaultShopItem,
            "gkits", defaultShopItem,
            "crates", defaultShopItem);

}
