package me.reklessmitch.mitchprisonscore.battlepass.object;

import lombok.Getter;

import java.util.List;

@Getter
public class Reward {
    String name;
    List<String> commands;

    public Reward(String name, List<String> commands) {
        this.name = name;
        this.commands = commands;
    }


}
