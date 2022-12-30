package tech.bison.trainee2021.playable.specificPlayableList.albumType.recordType;

import java.util.Collections;
import java.util.List;

import tech.bison.trainee2021.playable.Track;
import tech.bison.trainee2021.playable.specificPlayableList.albumType.Record;
import tech.bison.trainee2021.structure.Artist;

public class Single extends Record {

  public static final int SINGLE_TRACK_LIMIT = 1;

  /**
   * This constructor creates a new single album and writes it into the Commusify database
   * 
   * @param title
   *          Title of the new single
   * @param track
   *          The track of the single
   * @param interpreters
   *          All interpreters of the new single
   */
  public Single(String title, Track track, List<Artist> interpreters) {
    super(title, Collections.singletonList(track), interpreters);
  }

  /**
   * This constructor reads the existing single with the given ID from the Commusify
   * database
   * 
   * @param id
   *          The ID of the existing single
   */
  public Single(int id) {
    super(id);
  }

  @Override
  protected int limit() {
    return SINGLE_TRACK_LIMIT;
  }

  @Override
  protected AlbumType type() {
    return AlbumType.SINGLE;
  }
}
