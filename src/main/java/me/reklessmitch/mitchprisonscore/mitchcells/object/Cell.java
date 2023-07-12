package me.reklessmitch.mitchprisonscore.mitchcells.object;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Cell {

    String name;
    UUID owner;
    List<UUID> members;
    long beacons;

    public Cell(String name, UUID owner){
        this.name = name;
        this.owner = owner;
        beacons = 0;
        members = new ArrayList<>();
    }
}
