package tech.bison.trainee2021.playable.specificPlayableList.albumType.recordType;

import java.util.List;

import tech.bison.trainee2021.playable.Track;
import tech.bison.trainee2021.playable.specificPlayableList.albumType.Record;
import tech.bison.trainee2021.structure.Artist;

public class ExtendedPlay extends Record {

  public static final int EXTENDED_PLAY_TRACK_LIMIT = 5;

  /**
   * This constructor reads the existing extended play with the given ID from the Commusify
   * database
   * 
   * @param id
   *          The ID of the existing extended play
   */
  public ExtendedPlay(int id) {
    super(id);
  }

  /**
   * This constructor creates a new extended play and writes it into the Commusify database
   * 
   * @param title
   *          Title of the new extended play
   * @param tracks
   *          All tracks of the new extended play
   * @param interpreters
   *          All interpreters of the new extended play
   */
  public ExtendedPlay(String title, List<Track> tracks, List<Artist> interpreters) {
    super(title, tracks, interpreters);
  }

  @Override
  protected int limit() {
    return EXTENDED_PLAY_TRACK_LIMIT;
  }

  @Override
  protected AlbumType type() {
    return AlbumType.EXTENDED_PLAY;
  }
}
