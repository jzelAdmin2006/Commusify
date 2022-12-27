package tech.bison.trainee2021.playable.specificPlayableList.albumType.recordType;

import java.util.List;

import tech.bison.trainee2021.playable.Track;
import tech.bison.trainee2021.structure.Artist;

public class DoubleLongPlay extends LongPlay {

  public DoubleLongPlay(String title, List<Track> tracks, List<Artist> interpreters) {
    super(title, tracks, interpreters);
  }

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
