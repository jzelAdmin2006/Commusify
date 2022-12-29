package tech.bison.trainee2021.playable;

public class UnavailablePlayable implements Playable {

  private static final UnsupportedOperationException PLAYABLE_IS_NOT_AVAILABLE = new UnsupportedOperationException(
      "The playable isn't available.");

  @Override
  public String result() {
    throw PLAYABLE_IS_NOT_AVAILABLE;
  }

  @Override
  public int getId() {
    throw PLAYABLE_IS_NOT_AVAILABLE;
  }

  @Override
  public void play() {
    throw PLAYABLE_IS_NOT_AVAILABLE;
  }

  @Override
  public String playNext() {
    throw PLAYABLE_IS_NOT_AVAILABLE;
  }

  @Override
  public void playPrevious() {
    throw PLAYABLE_IS_NOT_AVAILABLE;
  }

  @Override
  public String shuffle(boolean shuffleIsOn) {
    throw PLAYABLE_IS_NOT_AVAILABLE;
  }

  @Override
  public void loop(boolean loopIsOn) {
    throw PLAYABLE_IS_NOT_AVAILABLE;
  }

  @Override
  public void download() {
    throw PLAYABLE_IS_NOT_AVAILABLE;
  }

  @Override
  public boolean isTrack() {
    throw PLAYABLE_IS_NOT_AVAILABLE;
  }
}
