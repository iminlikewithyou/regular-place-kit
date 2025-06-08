package games.omg.channeling.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import games.omg.channeling.Channel;

public class ChannelEvent extends Event {
  private static final HandlerList handlers = new HandlerList();
  private final Channel channel;

  public ChannelEvent(@NotNull Channel channel) {
    this.channel = channel;
  }

  public Channel getChannel() {
    return channel;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}
