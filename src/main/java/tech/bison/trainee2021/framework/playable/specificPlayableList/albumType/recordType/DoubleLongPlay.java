package tech.bison.trainee2021.framework.playable.specificPlayableList.albumType.recordType;

import java.util.List;

import tech.bison.trainee2021.framework.playable.Track;
import tech.bison.trainee2021.framework.structure.Artist;

public class DoubleLongPlay extends LongPlay {
  /**
   * This constructor creates a new double long play and writes it into the Commusify database
   * 
   * @param title
   *          Title of the new double long play
   * @param tracks
   *          All tracks of the new double long play
   * @param interpreters
   *          All interpreters of the new double long play
   */
  public DoubleLongPlay(String title, List<Track> tracks, List<Artist> interpreters) {
    super(title, tracks, interpreters);
  }

  /**
   * This constructor reads the existing double long play with the given ID from the Commusify
   * database
   * 
   * @param id
   *          The ID of the existing double long play
   */
  public DoubleLongPlay(int id) {
    super(id);
  }

  @Override
  protected int limit() {
    return super.limit() * 2;
  }

  @Override
  protected AlbumType type() {
    return AlbumType.DOUBLE_LONG_PLAY;
  }
}
