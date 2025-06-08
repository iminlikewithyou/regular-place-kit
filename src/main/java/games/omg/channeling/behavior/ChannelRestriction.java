package games.omg.channeling.behavior;

import games.omg.channeling.Channel;

/**
 * Channel restrictions are used to restrict the players from channeling.
 * 
 * can restrict playerfs form channeling if nto all palyers are in the menu
 */
public abstract class ChannelRestriction {

  // for access by listeners
  public Channel channel;

  public ChannelRestriction(Channel channel) {
    this.channel = channel;
  }
  
  // these classes need to call channel.reevaluateRestrictions() to check if a restriction needs to be unfulfilled

  /**
   * Checks if the restriction is fulfilled.
   * Channeling will be paused if any restriction is not fulfilled.
   * 
   * @return true if the restriction is fulfilled
   */
  abstract boolean isFulfilled();
}
