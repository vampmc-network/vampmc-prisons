package me.reklessmitch.mitchprisonscore.mitchmines.utils;

import lombok.Getter;
import me.reklessmitch.mitchprisonscore.mitchmines.configs.PlayerMine;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class BlockInPmineBrokeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final PlayerMine playerMine;
    private final Block block;

    public BlockInPmineBrokeEvent(Player player, PlayerMine playerMine, Block block){
        this.player = player;
        this.playerMine = playerMine;
        this.block = block;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
