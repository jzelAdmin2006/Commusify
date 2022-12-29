package tech.bison.trainee2021.playable;

public abstract class AvailablePlayable implements Playable {
  @Override
  public boolean isAvailable() {
    return true;
  }
}
