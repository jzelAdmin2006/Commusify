package tech.bison.trainee2021.playable.specificPlayableList.albumType;

import java.util.List;

import tech.bison.trainee2021.playable.Playable;
import tech.bison.trainee2021.playable.Track;
import tech.bison.trainee2021.playable.specificPlayableList.Album;
import tech.bison.trainee2021.structure.Artist;

public abstract class Record extends Album {
  protected abstract int limit();

  public Record(String title, List<Track> tracks, List<Artist> interpreters) {
    super(title, tracks, interpreters);
  }

  public Record(int id) {
    super(id);
  }

  @Override
  protected void addPlayables(int id, List<Playable> playables) {
    if (playables.size() + getPlayables().size() <= limit()) {
      super.addPlayables(id, playables);
    } else {
      throw new IllegalArgumentException(String
          .format("Records of type %s can't contain more than %s track(s).", getClass().getSimpleName(), limit()));
    }
  }
}