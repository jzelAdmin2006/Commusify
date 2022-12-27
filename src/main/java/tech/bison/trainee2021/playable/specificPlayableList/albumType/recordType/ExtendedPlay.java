package tech.bison.trainee2021.playable.specificPlayableList.albumType.recordType;

import java.util.List;

import tech.bison.trainee2021.playable.Track;
import tech.bison.trainee2021.playable.specificPlayableList.albumType.Record;
import tech.bison.trainee2021.structure.Artist;

public class ExtendedPlay extends Record {

  public static final int EXTENDED_PLAY_TRACK_LIMIT = 5;

  public ExtendedPlay(int id) {
    super(id);
  }

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
