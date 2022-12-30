package tech.bison.trainee2021.framework.playable;

public abstract class AvailablePlayable implements Playable {
  /**
   * Every playable is available except if it's invalid.
   */
  @Override
  public boolean isAvailable() {
    return true;
  }
}
