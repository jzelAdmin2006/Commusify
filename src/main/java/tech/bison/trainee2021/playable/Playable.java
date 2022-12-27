package tech.bison.trainee2021.playable;

public interface Playable {
  // TODO add implementation for this for all classes in this package
  public void play();

  public void playNext();

  public void playPrevious();

  public void shuffle(boolean shuffleIsOn);

  public void repeat(boolean repeatIsOn);
}
