package me.reklessmitch.mitchprisonscore.mitchprofiles.currency;

import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import org.bukkit.Bukkit;

import java.util.UUID;

public class MitchCurrency {
    String name;
    long amount;

    public MitchCurrency(String name, long amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public long getAmount() {
        return amount;
    }

    public void add(UUID player, long amount){
        this.amount += amount;
        Bukkit.getPlayer(player).sendMessage("(+" + amount + " " + getName() + ")");
        ProfilePlayer.get(player).changed();
    }

    public void add(long amount){
        this.amount += amount;
    }

    public void take(long amount){
        this.amount -= amount;
    }

    public void set(long amount){
        this.amount = amount;
    }

    public String convertToFigure(long number) {
        String[] suffixes = {"", "k", "m", "b", "t"};
        if (number < 1000) {
            return String.valueOf(number);
        }
        int magnitude = (int) (Math.log10(number) / 3);
        double convertedNumber = number / Math.pow(1000, magnitude);
        return String.format("%.1f%s", convertedNumber, suffixes[magnitude]);
    }
}
