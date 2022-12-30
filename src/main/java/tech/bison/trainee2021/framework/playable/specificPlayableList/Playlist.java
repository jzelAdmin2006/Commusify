package tech.bison.trainee2021.framework.playable.specificPlayableList;

import java.util.List;
import java.util.stream.Collectors;

import tech.bison.trainee2021.framework.playable.Playable;
import tech.bison.trainee2021.framework.playable.PlayableList;
import tech.bison.trainee2021.framework.playable.Track;
import tech.bison.trainee2021.userInterface.command.search.Searchable;

public class Playlist extends PlayableList {

  /**
   * This constructor creates a new playlist and writes it into the Commusify database
   * 
   * @param title
   *          Title of the new playlist
   * @param tracks
   *          All tracks of the new playlist
   */
  public Playlist(String title, List<Track> tracks) {
    super(title, tracks.stream().map(track -> (Playable) track).collect(Collectors.toList()));
  }

  /**
   * This constructor reads the existing playlist with the given ID from the Commusify
   * database
   * 
   * @param id
   *          The ID of the existing playlist
   */
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

  /**
   * @param track
   *          The track you want to add to the playlist
   */
  public void addTrack(Track track) {
    super.addPlayable(track);
  }

  @Override
  public String result() {
    return String.format("Playlist: (%s)", super.result());
  }

  public static class PlaylistSearcher extends PlayableListSearcher {
    @Override
    public Searchable of(int id) {
      return new Playlist(id);
    }
  }
}
