package games.omg.channeling;

/**
 * Represents a channel time for a specific type.
 */
public class ChannelTime {
  
  private ChannelType type;
  private int time;

  protected ChannelTime(ChannelType type, int time) {
    this.type = type;
    this.time = time;
  }

  /**
   * Gets the type of this ChannelTime.
   * 
   * @return The type
   */
  public ChannelType getType() {
    return type;
  }

  /**
   * Gets the time.
   * 
   * @return The time in seconds
   */
  public int getTime() {
    return time;
  }

  /**
   * Adds time to this ChannelTime.
   * 
   * @param time The time to add in seconds
   */
  public void addTime(int time) {
    this.time += time;
  }

  /**
   * Removes time from this ChannelTime.
   * 
   * If the time you're trying to remove is greater than the time in this ChannelTime,
   * the time in this ChannelTime will be set to 0 and the remaining time will be returned.
   * 
   * @param time The time to remove in seconds
   * @return The remaining time that was not removed
   */
  public int removeTime(int time) {
    if (this.time - time < 0) {
      int t = this.time;
      this.time = 0;
      return time - t;
    }
    this.time -= time;
    return 0;
  }
  
  /**
   * Adds time to this ChannelTime if the ChannelTime is the same type.
   * 
   * @param channelTime The ChannelTime to add
   * @return Whether or not the channel time was added
   */
  public boolean add(ChannelTime channelTime) {
    if (channelTime.equals(this)) {
      this.addTime(channelTime.getTime());
      return true;
    }
    return false;
  }
  
  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof ChannelTime)) return false;
    ChannelTime ct = (ChannelTime) o;
    return ct.getType().equals(type);
  }
}
