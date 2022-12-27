package tech.bison.trainee2021.playable;

public interface Playable {
  public int getId();

  public void play();

  public void playNext();

  public void playPrevious();

  public void shuffle(boolean shuffleIsOn);

  public void repeat(boolean repeatIsOn);

  public void download();
}
