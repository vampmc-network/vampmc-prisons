package me.reklessmitch.mitchprisonscore.mitchprofiles.configs;

import com.massivecraft.massivecore.store.SenderEntity;
import lombok.Getter;
import lombok.Setter;
import me.reklessmitch.mitchprisonscore.mitchprofiles.currency.MitchCurrency;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProfilePlayer extends SenderEntity<ProfilePlayer> {
    public static ProfilePlayer get(Object oid) {
        return ProfilePlayerColl.get().get(oid);
    }

    @Getter List<MitchCurrency> currencyList = ProfilesConf.get().getCurrencyList().stream().collect(ArrayList::new, (list, str) -> list.add(new MitchCurrency(str, 0)), ArrayList::addAll);
    List<UUID> friends = new ArrayList<>();
    @Getter @Setter private String joinMessage = "";

    @Override
    public ProfilePlayer load(@NotNull ProfilePlayer that)
    {
        super.load(that);
        return this;
    }

    public MitchCurrency getCurrency(String currencyID){
        return getCurrencyList().stream()
                .filter(c -> c.getName().equals(currencyID))
                .findFirst().orElse(null);
    }

}
