package me.reklessmitch.mitchprisonscore.mitchbattlepass.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class BlocksMinedEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final long blocksBroken;

    public BlocksMinedEvent(Player player, long blocksBroken) {
        this.player = player;
        this.blocksBroken = blocksBroken;
    }

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlers;
    }
}
