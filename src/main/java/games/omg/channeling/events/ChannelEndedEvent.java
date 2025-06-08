package games.omg.channeling.events;

import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import games.omg.channeling.Channel;

public class ChannelEndedEvent extends ChannelEvent {
  private static final HandlerList handlers = new HandlerList();

  public ChannelEndedEvent(@NotNull Channel channel) {
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
