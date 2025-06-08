package games.omg.channeling.events;

import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import games.omg.channeling.Channel;

public class ChannelUpdatedEvent extends ChannelEvent {
  private static final HandlerList handlers = new HandlerList();

  public ChannelUpdatedEvent(@NotNull Channel channel) {
    super(channel);
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}
