package games.omg.channeling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import games.omg.channeling.states.ChannelStartedResult;

/**
 * A class which represents a Channel, storing ChannelTimes and managing them.
 * 
 * (use some default ChannelRestrictions?)
 * maybe a TeleportChannel class which automatically creates those channelrestrictions
 * 
 * Channel
 *  .create(player)
 *  .add(ChannelType.TRAVEL_TIME.length(10))
 *  .channel();
 */
public class Channel {

  // need to name the channel and give it an id,
  // so if a person leaves a channel party, they can rejoin, which includes a helpful name from the chat
  // like: You left the "channel name" channel. You can rejoin by typing /channel join "channel name"

  // boolean canRejoin
  // boolean cancelWhenAllLeave

  private static HashMap<Player, Channel> channels = new HashMap<>();

  private List<ChannelTime> channelTimes;
  private List<Player> players;
  // private ChannelRunner runner;

  /**
   * Creates a new Channel.
   * 
   * You should specify the players and add channel times later.
   */
  private Channel(List<Player> players) {
    this.channelTimes = new ArrayList<>();
    this.players = players;
  }

  /**
   * Creates a new Channel for the specified player.
   * 
   * @param player The player to create the Channel for
   * @return The created Channel
   */
  public static Channel create(Player player) {
    List<Player> players = new ArrayList<>();
    players.add(player);
    return new Channel(players);
  }

  /**
   * Creates a new Channel for the specified players.
   * 
   * @param players The players to create the Channel for
   * @return The created Channel
   */
  public static Channel create(List<Player> players) {
    return new Channel(players);
  }

  /**
   * Creates a new Channel for the specified players.
   * 
   * @param players The players to create the Channel for
   * @return The created Channel
   */
  public static Channel create(Player... players) {
    List<Player> playerList = new ArrayList<>();
    for (Player player : players) playerList.add(player);
    return new Channel(playerList);
  }

  /**
   * Adds a ChannelTime to this Channel.
   * 
   * @param channelTime The ChannelTime to add
   * @return This Channel
   */
  public Channel add(ChannelTime channelTime) {
    for (ChannelTime ct : channelTimes) {
      if (ct.add(channelTime)) {
        return this;
      }
    }
    if (channelTime.getTime() == 0) return this;
    channelTimes.add(channelTime);
    return this;
  }

  /**
   * Adds ChannelTimes to this Channel.
   * 
   * @param channelTimes The ChannelTimes to add
   * @return This Channel
   */
  public Channel add(List<ChannelTime> channelTimes) {
    for (ChannelTime channelTime : channelTimes) add(channelTime);
    return this;
  }

  /**
   * Adds ChannelTimes to this Channel.
   * @param channelTimes The ChannelTimes to add
   * @return This Channel
   */
  public Channel add(ChannelTime... channelTimes) {
    for (ChannelTime channelTime : channelTimes) add(channelTime);
    return this;
  }

  /**
   * Gets the Channel a Player is channeling.
   * 
   * @param player The Player to get the Channel of
   * @return The Channel of the Player
   */
  public static Channel getChannelOf(Player player) {
    return channels.get(player);
  }

  /**
   * Gets the time remaining in seconds.
   * 
   * @return The time remaining in seconds
   */
  public int getRemainingTime() {
    int time = 0;
    for (ChannelTime channelTime : channelTimes) time += channelTime.getTime();
    return time;
  }

  /**
   * Removes time from the ChannelTimes in order, removing from the next if the current one hits 0.
   * 
   * @param time The time to remove in seconds
   * @return The remaining time that was not removed
   */
  public int removeTime(int time) {
    for (ChannelTime channelTime : channelTimes) {
      time = channelTime.removeTime(time);
      if (time == 0) return 0;
    }
    return time;
  }
  
  /**
   * Removes 1 second from the first ChannelTime that has time remaining.
   * 
   * @return Whether or not this Channel has completed
   */
  public boolean next() {
    return removeTime(1) > 0;
  }

  /**
   * Checks if all channel times in this Channel are 0.
   * 
   * @return Whether or not all channel times are 0
   */
  public boolean isComplete() {
    for (ChannelTime channelTime : channelTimes) {
      if (channelTime.getTime() > 0) return false;
    }
    return true;
  }

  // /**
  //  * Gets the ChannelRunner for this Channel.
  //  * 
  //  * @return The ChannelRunner
  //  */
  // protected ChannelRunner getRunner() {
  //   return runner;
  // }

  public ChannelStartedResult channel() {
    // Check if any players are already channeling
    for (Player player : players) {
      if (channels.containsKey(player)) return ChannelStartedResult.ALREADY_CHANNELING;
    }
    
    if (isComplete()) {
      // This channel is already complete



      return ChannelStartedResult.CHANNEL_STARTED;
    } else {
      // Start the channel
      for (Player player : players) {
        channels.put(player, this);
      }

      // runner = new ChannelRunner(this);


    }
    
    return ChannelStartedResult.CHANNEL_STARTED;
  }

  // what happens if a player leaves during the channel?
  // or dies during the channel?
  // if multiple people are channeling, and one of them is interrupted, what happens to the others?
  // what happens if a player is added during the channel?
  // what happens if a player is removed during the channel?

  // you should be able to close the inventory and reopen it and continue channeling for types of channels
  // that want to allow it

  // random extraneous crazy idea use case thought experiment:
  // user is in a "dungeon" area .... 
  // can teleport to spawn to exit
  // can't teleport to other players ?
  // maybe that "denuggneogn" is entered by many users and in order to "complete" it it requires everyone to reach the end and start channeling
  // 
}