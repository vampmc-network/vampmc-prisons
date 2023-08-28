package me.reklessmitch.mitchprisonscore.mitchboosters.utils;

import me.reklessmitch.mitchprisonscore.mitchboosters.configs.BoosterPlayer;
import me.reklessmitch.mitchprisonscore.mitchboosters.objects.Booster;
import org.bukkit.scheduler.BukkitRunnable;

public class BoosterActiveTask extends BukkitRunnable{

    private final Booster booster;
    private final BoosterPlayer player;

    public BoosterActiveTask(Booster booster, BoosterPlayer player) {
        this.booster = booster;
        this.player = player;
    }

    @Override
    public void run() {
        long timeLeft = booster.getTimeInSeconds();
        if (timeLeft <= 0) {
            cancel();
            player.getPlayer().sendMessage("§cYour " + booster.getType() + " booster has expired");
            player.getBoosters().remove(booster);
            player.deactivateBooster(booster);
            player.changed();
            return;
        }
        if(!booster.isActive()){
            player.getPlayer().sendMessage("§cYour " + booster.getType() + " booster has been deactivated");
            player.deactivateBooster(booster);
            player.changed();
            cancel();
            return;
        }

        booster.setTimeInSeconds(booster.getTimeInSeconds() - 1);
        if(timeLeft % 60 == 0) {
            player.changed();
        }
    }
}
