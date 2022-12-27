package tech.bison.trainee2021.playable.specificPlayableList.albumType.recordType;

import java.util.List;

import tech.bison.trainee2021.playable.Track;
import tech.bison.trainee2021.playable.specificPlayableList.albumType.Record;
import tech.bison.trainee2021.structure.Artist;

public class Single extends Record {

  private static final int SINGLE_TRACK_LIMIT = 1;

  public Single(String title, List<Track> tracks, List<Artist> interpreters) {
    super(title, tracks, interpreters);
  }

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
