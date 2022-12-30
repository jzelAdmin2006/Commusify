package tech.bison.trainee2021.framework.playable.specificPlayableList.albumType.recordType;

import java.util.List;

import tech.bison.trainee2021.framework.playable.Track;
import tech.bison.trainee2021.framework.playable.specificPlayableList.albumType.Record;
import tech.bison.trainee2021.framework.structure.Artist;

public class LongPlay extends Record {

  public static final int LONGPLAY_TRACK_LIMIT = 25;

  /**
   * This constructor creates a new long play and writes it into the Commusify database
   * 
   * @param title
   *          Title of the new long play
   * @param tracks
   *          All tracks of the new long play
   * @param interpreters
   *          All interpreters of the new long play
   */
  public LongPlay(String title, List<Track> tracks, List<Artist> interpreters) {
    super(title, tracks, interpreters);
  }

  /**
   * This constructor reads the existing long play with the given ID from the Commusify
   * database
   * 
   * @param id
   *          The ID of the existing long play
   */
  public LongPlay(int id) {
    super(id);
  }

  @Override
  protected int limit() {
    return LONGPLAY_TRACK_LIMIT;
  }

  @Override
  protected AlbumType type() {
    return AlbumType.LONG_PLAY;
  }
}
