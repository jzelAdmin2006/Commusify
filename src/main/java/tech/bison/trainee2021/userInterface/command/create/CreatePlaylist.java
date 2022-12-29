package tech.bison.trainee2021.userInterface.command.create;

import java.util.List;
import java.util.stream.Collectors;

import tech.bison.trainee2021.playable.Playable;
import tech.bison.trainee2021.playable.Track;
import tech.bison.trainee2021.playable.specificPlayableList.Playlist;

public class CreatePlaylist extends CreatePlayableList {
  @Override
  protected String addPlayable(Playable playable) {
    if (playable.isTrack()) {
      return super.addPlayable(playable);
    } else {
      return String.format("The playable with the id %s isn't a track. Playlists can only contain tracks.",
          playable.getId());
    }
  }

  @Override
  public String createPlayableList(List<Playable> initializerPlayable) {
    List<Track> initializerTrack = initializerPlayable.stream()
        .map(playable -> (Track) playable)
        .collect(Collectors.toList());
    Playlist playlist = new Playlist(arguments.get(0), initializerTrack);
    return String.format("The playlist was created an received the id %s.", playlist.getId());
  }

  @Override
  protected String getPlayableSpellings() {
    return String.format("%s (in this case, only tracks, incl. track edit types are allowed)",
        super.getPlayableSpellings());
  }
}
