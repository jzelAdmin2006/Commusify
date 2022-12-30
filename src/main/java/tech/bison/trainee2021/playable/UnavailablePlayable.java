package tech.bison.trainee2021.playable;

public class UnavailablePlayable implements Playable {

  /**
   * This exception can be used for unsupported operations of an unavailable playable.
   */
  private static final UnsupportedOperationException PLAYABLE_IS_NOT_AVAILABLE = new UnsupportedOperationException(
      "The playable isn't available.");

  /**
   * @deprecated This should be a sign not to use this method in general.
   */
  @Deprecated
  @Override
  public String result() {
    throw PLAYABLE_IS_NOT_AVAILABLE;
  }

  /**
   * @deprecated This should be a sign not to use this method in general.
   */
  @Deprecated
  @Override
  public int getId() {
    throw PLAYABLE_IS_NOT_AVAILABLE;
  }

  /**
   * @deprecated This should be a sign not to use this method in general.
   */
  @Deprecated
  @Override
  public void play() {
    throw PLAYABLE_IS_NOT_AVAILABLE;
  }

  /**
   * @deprecated This should be a sign not to use this method in general.
   */
  @Deprecated
  @Override
  public String playNext() {
    throw PLAYABLE_IS_NOT_AVAILABLE;
  }

  /**
   * @deprecated This should be a sign not to use this method in general.
   */
  @Deprecated
  @Override
  public void playPrevious() {
    throw PLAYABLE_IS_NOT_AVAILABLE;
  }

  /**
   * @deprecated This should be a sign not to use this method in general.
   */
  @Deprecated
  @Override
  public String shuffle(boolean shuffleIsOn) {
    throw PLAYABLE_IS_NOT_AVAILABLE;
  }

  /**
   * @deprecated This should be a sign not to use this method in general.
   */
  @Deprecated
  @Override
  public void loop(boolean loopIsOn) {
    throw PLAYABLE_IS_NOT_AVAILABLE;
  }

  /**
   * @deprecated This should be a sign not to use this method in general.
   */
  @Deprecated
  @Override
  public void download() {
    throw PLAYABLE_IS_NOT_AVAILABLE;
  }

  /**
   * An unavailable playable isn't available.
   */
  @Override
  public boolean isAvailable() {
    return false;
  }
}
