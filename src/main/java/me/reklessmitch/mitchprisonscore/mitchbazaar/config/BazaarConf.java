package me.reklessmitch.mitchprisonscore.mitchbazaar.config;

import com.massivecraft.massivecore.store.Entity;
import lombok.Getter;
import me.reklessmitch.mitchprisonscore.mitchbazaar.object.ShopValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BazaarConf extends Entity<BazaarConf> {

    public static BazaarConf i;
    public static BazaarConf get() {return i;}

    @Getter
    private Map<String, Map<String, List<ShopValue>>> sellPrices = Map.of(
            "beacon", Map.of(
                    "token", new ArrayList<>(),
                    "money", new ArrayList<>(),
                    "credits", new ArrayList<>()
            ),
            "token", Map.of(
                    "money", new ArrayList<>(),
                    "credits", new ArrayList<>(),
                    "beacon", new ArrayList<>()
            ),
            "money", Map.of(
                    "token", new ArrayList<>(),
                    "credits", new ArrayList<>(),
                    "beacon", new ArrayList<>()
            ),
            "credits", Map.of(
                    "token", new ArrayList<>(),
                    "money", new ArrayList<>(),
                    "beacon", new ArrayList<>()
            )
    );

}