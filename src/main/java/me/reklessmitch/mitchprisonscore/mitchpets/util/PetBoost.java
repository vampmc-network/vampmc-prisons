package me.reklessmitch.mitchprisonscore.mitchpets.util;

import lombok.Getter;

@Getter
public class PetBoost {

    double baseBoost;
    double increasePerLevel;

    public PetBoost(double baseBoost, double increasePerLevel) {
        this.baseBoost = baseBoost;
        this.increasePerLevel = increasePerLevel;
    }

    public double getBoost(int level){
        return baseBoost + (increasePerLevel * level);
    }
}
