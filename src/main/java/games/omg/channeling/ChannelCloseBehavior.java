package games.omg.channeling;

public enum ChannelCloseBehavior {
  /**
   * When a player closes the channel, they are removed from the party.
   */
  CAUSE_PARTY_LEAVE,
  
  /**
   * When any player closes the channel, the channel is cancelled entirely.
   */
  CAUSE_CHANNEL_CANCEL,

  /**
   * When a player closes the channel, the channel is paused and can be rejoined.
   */
  PAUSE_CHANNEL;

  // this would be better as some sort of class like .useBehavior(new RestartFromBeginning())
  // i mean like some sort of function really, so that the behavior can vary depending on some cases
  public enum ChannelPauseBehavior {
    /**
     * When a channel is paused, it will restart from the beginning when all players rejoin.
     */
    RESTART_FROM_BEGINNING,

    /**
     * When a channel is paused, it will continue from where it left off when all players rejoin.
     */
    CONTINUE_FROM_PAUSE;
  }
}
