package games.omg.channeling;

import games.omg.channeling.events.ChannelEndedEvent;
import games.omg.channeling.events.ChannelUpdatedEvent;

public abstract class ChannelRunner {
  
  public void onUpdate(ChannelUpdatedEvent event) {
    
  }
  
  public abstract void onInterrupt(ChannelEndedEvent event);
  public abstract void onFinish();
}
