package tech.bison.trainee2021.playable.specificPlayableList.albumType.recordType;

import java.util.List;

import tech.bison.trainee2021.playable.Track;
import tech.bison.trainee2021.playable.specificPlayableList.albumType.Record;
import tech.bison.trainee2021.structure.Artist;

public class LongPlay extends Record {

  public static final int LONGPLAY_TRACK_LIMIT = 25;

  public LongPlay(String title, List<Track> tracks, List<Artist> interpreters) {
    super(title, tracks, interpreters);
  }

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
