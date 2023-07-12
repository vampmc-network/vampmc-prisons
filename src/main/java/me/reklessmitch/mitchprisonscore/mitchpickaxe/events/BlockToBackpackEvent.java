package me.reklessmitch.mitchprisonscore.mitchpickaxe.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class BlockToBackpackEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final int amount;

    public BlockToBackpackEvent(Player player, int amount) {
        this.player = player;
        this.amount = amount;
    }

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlers;
    }
}
