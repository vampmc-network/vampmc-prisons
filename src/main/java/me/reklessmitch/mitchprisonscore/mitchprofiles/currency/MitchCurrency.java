package me.reklessmitch.mitchprisonscore.mitchprofiles.currency;

import me.reklessmitch.mitchprisonscore.mitchbackpack.config.BackpackPlayer;
import me.reklessmitch.mitchprisonscore.mitchpickaxe.configs.PPickaxe;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import org.bukkit.Bukkit;

import java.math.BigInteger;
import java.util.UUID;

public class MitchCurrency {
    private String name;
    private BigInteger amount;

    public MitchCurrency(String name, BigInteger amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void add(UUID player, BigInteger a){
        amount = amount.add(a);
        ProfilePlayer profilePlayer = ProfilePlayer.get(player);
        Bukkit.getPlayer(player).sendMessage("(+" + amount + " " + getName() + ")");
        if(PPickaxe.get(player).isAutoRankup()){
            profilePlayer.rankUpMax();
        }
        profilePlayer.changed();
    }

    public void add(BigInteger a){
        amount = amount.add(a);
    }

    public void add(long a){
        amount = amount.add(BigInteger.valueOf(a));
    }

    public void take(BigInteger a){
        amount = amount.subtract(a);
    }

    public void take(long a){
        amount = amount.subtract(BigInteger.valueOf(a));
    }

    public void set(BigInteger a){
        this.amount = a;
    }

}
