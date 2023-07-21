package me.reklessmitch.mitchprisonscore.mitchpets.entity;

import lombok.Getter;
import me.reklessmitch.mitchprisonscore.MitchPrisonsCore;

@Getter
public class Pet {

    private final PetType type;
    private int level;

    public Pet(PetType type) {
        this.type = type;
        this.level = 1;
    }

    public void addLevel(int level) {
        this.level += level;
    }

    public void removeLevel(int level) {
        this.level -= level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getPetBooster(){
        return Double.parseDouble(MitchPrisonsCore.get().getDecimalFormat()
                .format(PetConf.get().getPetBoosts().get(type).getBoost(level)));
    }

    public void spawn(){

    }
}
