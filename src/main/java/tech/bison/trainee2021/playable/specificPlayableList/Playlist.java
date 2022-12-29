package tech.bison.trainee2021.playable.specificPlayableList;

import java.util.List;
import java.util.stream.Collectors;

import tech.bison.trainee2021.playable.Playable;
import tech.bison.trainee2021.playable.PlayableList;
import tech.bison.trainee2021.playable.Track;

public class Playlist extends PlayableList {

  public Playlist(String string, List<Track> tracks) {
    super(string, tracks.stream().map(track -> (Playable) track).collect(Collectors.toList()));
  }

  public Playlist(int id) {
    super(id);
  }

  @Deprecated
  @Override
  public void addPlayable(Playable playable) {
    if (playable.isTrack()) {
      addTrack((Track) playable);
    } else {
      throw new UnsupportedOperationException("A playlist can only contain tracks.");
    }
  }

  public void addTrack(Track track) {
    super.addPlayable(track);
  }
}
