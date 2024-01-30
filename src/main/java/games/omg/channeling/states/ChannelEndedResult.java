package games.omg.channeling.states;

public enum ChannelEndedResult {
  /**
   * The player interrupted the channel.
   */
  PLAYER_INTERRUPTED(false),

  /**
   * The channel was interrupted by an external source.
   */
  CHANNEL_INTERRUPTED(false),

  /**
   * The channel was completed.
   */
  COMPLETED(true),
  
  /**
   * The channel was completed instantly.
   */
  COMPLETED_INSTANTLY(true);

  private final boolean finished;

  ChannelEndedResult(boolean completed) {
    this.finished = completed;
  }

  public boolean isFinished() {
    return finished;
  }

  public boolean isInterrupted() {
    return !finished;
  }
}
