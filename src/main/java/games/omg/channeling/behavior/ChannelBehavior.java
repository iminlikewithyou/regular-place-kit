package games.omg.channeling.behavior;

import games.omg.channeling.events.ChannelClosedEvent;

/**
 * the ChannelClosedEvent or ChannelPausedEvent is called and then relayed to
 * Channels with behaviors for that corresponding Channel..?
 */
public abstract class ChannelBehavior {

  public void onPlayerClosedChannel(ChannelClosedEvent event) {
  }

  // public void onChannelPaused(ChannelPausedEvent event) {
  // }

}
