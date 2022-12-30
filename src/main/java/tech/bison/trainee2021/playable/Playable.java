package tech.bison.trainee2021.playable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import tech.bison.trainee2021.playable.Playable.PlayableSearcher.KnownPlayable;
import tech.bison.trainee2021.playable.PlayableList.PlayableListIdChecker;
import tech.bison.trainee2021.playable.PlayableList.PlayableListSearcher;
import tech.bison.trainee2021.playable.Track.TrackIdChecker;
import tech.bison.trainee2021.playable.Track.TrackSearcher;
import tech.bison.trainee2021.playable.specificPlayableList.Album;
import tech.bison.trainee2021.playable.specificPlayableList.Album.AlbumIdChecker;
import tech.bison.trainee2021.playable.specificPlayableList.Album.AlbumSearcher;
import tech.bison.trainee2021.playable.specificPlayableList.Playlist;
import tech.bison.trainee2021.playable.specificPlayableList.Playlist.PlaylistSearcher;
import tech.bison.trainee2021.userInterface.command.search.Searchable;
import tech.bison.trainee2021.userInterface.command.search.Searcher;

public interface Playable extends Searchable {
  /**
   * @return Unique ID of the playable
   */
  public int getId();

  /**
   * Opens the default music player of the host operating system and plays the playable with it.
   */
  public void play();

  /**
   * Tries to play the next sub playable
   * 
   * @return message if the operation was successful
   */
  public String playNext();

  /**
   * Tries to play the previous sub playable. If that's not possible it'll replay the current
   * playable.
   */
  public void playPrevious();

  /**
   * @param shuffleIsOn
   *          True = Shuffle should be turned on
   *          False = Shuffle should be turned off
   * @return message if the operation was successful
   */
  public String shuffle(boolean shuffleIsOn);

  /**
   * @param loopIsOn
   *          True = Looping should be turned on
   *          False = Looping should be turned off
   */
  public void loop(boolean loopIsOn);

  /**
   * Downloads the playable to the downloads-folder in the user's home directory
   */
  public void download();

  /**
   * @return True = This playable is a simple single track, it can also be an edit type
   *         False = This is a track collection, like for example a playable list or an album
   */
  public default boolean isTrack() {
    return false;
  }

  /**
   * @return True = This playable is available in the Commusify database
   *         False = This playable isn't available in the Commusify database
   */
  public boolean isAvailable();

  /**
   * @param id
   *          The ID of the playable that you want to get from the Commusify database
   * @param knownPlayable
   *          The known playable type of the playable you want
   * @return The playable of the Commusify Playable you entered the ID and known playable type of
   */
  public static Playable of(int id, KnownPlayable knownPlayable) {
    if (idExists(id, knownPlayable)) {
      switch (knownPlayable) {
        case PLAYABLE_LIST:
          return new PlayableList(id);
        case TRACK:
          return new Track(id);
        case ALBUM:
          return Album.of(id);
        case PLAYLIST:
          return new Playlist(id);
        case NOT_FOUND:
          // this can't happen because of the check if the ID exists
          break;
      }
      // should never happen
      throw new UnsupportedOperationException(
          String.format("The known playable automatic type selection %s isn't implemented.", knownPlayable));
    } else {
      return new UnavailablePlayable();
    }
  }

  /**
   * @param id
   *          the ID of the playable you want to check if the ID exists
   * @param knownPlayable
   *          the known playable type of the playable you want to check if the ID exists
   * @return True = the ID of the entered playable exists in the Commusify database
   *         False = the ID of the entered playable doesn't exist in the Commusify database, if a
   *         playable object is created with this ID, exceptions might occur
   */
  private static boolean idExists(int id, KnownPlayable knownPlayable) {
    switch (knownPlayable) {
      case NOT_FOUND:
        return false;
      case PLAYABLE_LIST:
      case PLAYLIST:
        return new PlayableListIdChecker().exists(id);
      case TRACK:
        return new TrackIdChecker().exists(id);
      case ALBUM:
        return new AlbumIdChecker().exists(id);
    }
    // should never happen
    throw new UnsupportedOperationException(
        String.format("The check if an id exists isn't implemented for the known playable type %s.", knownPlayable));
  }

  public static class PlayableSearcher extends Searcher {
    /**
     * Contains the supported known playable types
     */
    public enum KnownPlayable {
      PLAYLIST,
      ALBUM,
      PLAYABLE_LIST,
      TRACK,
      NOT_FOUND;

      private static final String PLAYABLE_LIST_SPELLING = "PlayableList";
      private static final String TRACK_SPELLING = "Track";
      private static final String ALBUM_SPELLING = "Album";
      private static final String PLAYLIST_SPELLING = "Playlist";
      private static final String NOT_FOUND_SPELLING_MESSAGE = "If your playable type is invalid, the message will tell you.";

      /**
       * @param search
       *          What you want to search for
       * @return A list of playables that were found as a search result
       */
      private List<Searchable> search(String search) {
        switch (this) {
          case PLAYABLE_LIST:
            return new PlayableListSearcher().search(search);
          case TRACK:
            return new TrackSearcher().search(search);
          case NOT_FOUND:
            return new ArrayList<>();
          case ALBUM:
            return new AlbumSearcher().search(search);
          case PLAYLIST:
            return new PlaylistSearcher().search(search);
        }
        // should never happen
        throw new UnsupportedOperationException(
            String.format("The searching for the known playable %s isn't implemented", this));
      }

      /**
       * @return Spelling of the known playable type
       */
      public String spelling() {
        switch (this) {
          case PLAYABLE_LIST:
            return PLAYABLE_LIST_SPELLING;
          case TRACK:
            return TRACK_SPELLING;
          case NOT_FOUND:
            return NOT_FOUND_SPELLING_MESSAGE;
          case ALBUM:
            return ALBUM_SPELLING;
          case PLAYLIST:
            return PLAYLIST_SPELLING;
        }
        // should never happen
        throw new UnsupportedOperationException(
            String.format("The spelling for the known playable %s isn't implemented.", this));
      }

      /**
       * @param spelling
       *          Spelling of the playable type
       * @return Known playable type
       */
      public static KnownPlayable translate(String spelling) {
        for (KnownPlayable knownPlayable : KnownPlayable.values()) {
          if (knownPlayable != NOT_FOUND && spelling.equals(knownPlayable.spelling())) {
            return knownPlayable;
          }
        }
        return NOT_FOUND;
      }

      /**
       * @return All the playable type spellings formatted sensibly
       */
      public static String getSpellings() {
        return Arrays.asList(KnownPlayable.values())
            .stream()
            .map(knownPlayable -> knownPlayable.spelling())
            .collect(Collectors.toList())
            .stream()
            .map(String::valueOf)
            .collect(Collectors.joining(" / "));
      }
    }

    @Override
    public List<Searchable> search(String search) {
      List<Searchable> searchables = new ArrayList<>();
      for (KnownPlayable knownPlayable : KnownPlayable.values()) {
        searchables.addAll(knownPlayable.search(search));
      }
      return searchables;
    }

    /**
     * @deprecated This should be a sign not to use this method in general.
     */
    @Deprecated
    @Override
    protected String getSearchCallSP() {
      throw new UnsupportedOperationException("There is no stored procedure for a playable itself.");
    }

    /**
     * @deprecated This should be a sign not to use this method in general.
     */
    @Deprecated
    @Override
    public Searchable of(int id) {
      throw new UnsupportedOperationException("You can't create a generic playable.");
    }
  }
}
