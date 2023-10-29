package me.reklessmitch.mitchprisonscore.mitchprofiles.configs;

import com.massivecraft.massivecore.store.SenderEntity;
import lombok.Getter;
import lombok.Setter;
import me.reklessmitch.mitchprisonscore.MitchPrisonsCore;
import me.reklessmitch.mitchprisonscore.colls.ProfilePlayerColl;
import me.reklessmitch.mitchprisonscore.mitchbackpack.config.BackpackPlayer;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import me.reklessmitch.mitchprisonscore.mitchprofiles.currency.MitchCurrency;
import me.reklessmitch.mitchprisonscore.mitchrankup.config.RankupConf;
import me.reklessmitch.mitchprisonscore.mitchrankup.utils.RankUpTask;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class ProfilePlayer extends SenderEntity<ProfilePlayer> {
    public static ProfilePlayer get(Object oid) {
        return ProfilePlayerColl.get().get(oid);
    }

    private List<MitchCurrency> currencyList = ProfilesConf.get().getCurrencyList().stream()
            .collect(ArrayList::new, (list, str) -> list.add(new MitchCurrency(str, BigInteger.ZERO)), ArrayList::addAll);
    private List<UUID> friends = new ArrayList<>();
    @Setter private String joinMessage = "";
    private List<Integer> claimedRewards = new ArrayList<>();
    @Setter private int rank = 0;


    @Override
    public ProfilePlayer load(@NotNull ProfilePlayer that)
    {
        super.load(that);
        return this;
    }

    public void rankUpMax(){
        MitchCurrency money = getCurrency("money");
        new RankUpTask(getPlayer(), this, money, RankupConf.get()).runTaskAsynchronously(MitchPrisonsCore.get());
    }

    public MitchCurrency getCurrency(String currencyID){
        if(currencyID.equals("tokens") || currencyID.equals("t")){
            currencyID = "token";
        }
        if(currencyID.equals("beacons") || currencyID.equals("b")){
            currencyID = "beacon";
        }
        String finalCurrencyID = currencyID;
        return getCurrencyList().stream()
                .filter(c -> c.getName().equals(finalCurrencyID))
                .findFirst().orElse(null);
    }
}
