package tech.bison.trainee2021.playable;

import java.util.ArrayList;
import java.util.List;

import tech.bison.trainee2021.playable.Playable.PlayableSearcher.KnownPlayable;
import tech.bison.trainee2021.playable.PlayableList.PlayableListSearcher;
import tech.bison.trainee2021.playable.Track.TrackSearcher;
import tech.bison.trainee2021.userInterface.command.search.Searchable;
import tech.bison.trainee2021.userInterface.command.search.Searcher;

public interface Playable extends Searchable {
  public int getId();

  public void play();

  public String playNext();

  public void playPrevious();

  public String shuffle(boolean shuffleIsOn);

  public void loop(boolean loopIsOn);

  public void download();

  public boolean isTrack();

  public boolean isAvailable();

  public static Playable of(int id, KnownPlayable knownPlayable) throws UnsupportedOperationException {
    if (idExists(id, knownPlayable)) {
      switch (knownPlayable) {
        case NOT_FOUND:
          break;
        case PLAYABLE_LIST:
          return new PlayableList(id);
        case TRACK:
          return new Track(id);
      }
      throw new UnsupportedOperationException(
          String.format("The known playable automatic type selection %s isn't implemented.", knownPlayable));
    } else {
      return new UnavailablePlayable();
    }
  }

  private static boolean idExists(int id, KnownPlayable knownPlayable) {
    switch (knownPlayable) {
      case NOT_FOUND:
        return false;
      case PLAYABLE_LIST:
        return PlayableList.idExists(id);
      case TRACK:
        return Track.idExists(id);
    }
    // should never happen
    throw new UnsupportedOperationException(
        String.format("The check if an id exists isn't implemented for the known playable type %s.", knownPlayable));
  }

  public static class PlayableSearcher extends Searcher {
    public enum KnownPlayable {
      PLAYABLE_LIST,
      TRACK,
      NOT_FOUND;

      private static final String PLAYABLE_LIST_SPELLING = "PlayableList";
      private static final String TRACK_SPELLING = "Track";
      private static final String NOT_FOUND_SPELLING_MESSAGE = "If your playable type is invalid, the message will tell you.";

      private List<Searchable> search(String search) {
        switch (this) {
          case PLAYABLE_LIST:
            return new PlayableListSearcher().search(search);
          case TRACK:
            return new TrackSearcher().search(search);
          case NOT_FOUND:
            return new ArrayList<>();
        }
        // should never happen
        throw new UnsupportedOperationException(
            String.format("The searching for the known playable %s isn't implemented", this));
      }

      public String spelling() {
        switch (this) {
          case PLAYABLE_LIST:
            return PLAYABLE_LIST_SPELLING;
          case TRACK:
            return TRACK_SPELLING;
          case NOT_FOUND:
            return NOT_FOUND_SPELLING_MESSAGE;
        }
        // should never happen
        throw new UnsupportedOperationException(
            String.format("The spelling for the known playable %s isn't implemented.", this));
      }

      public static KnownPlayable translate(String spelling) {
        for (KnownPlayable knownPlayable : KnownPlayable.values()) {
          if (knownPlayable != NOT_FOUND && spelling.equals(knownPlayable.spelling())) {
            return knownPlayable;
          }
        }
        return NOT_FOUND;
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

    @Deprecated
    @Override
    protected String getSearchCallSP() {
      throw new UnsupportedOperationException("There is no stored procedure for a playable itself.");
    }

    @Deprecated
    @Override
    public Searchable of(int id) {
      throw new UnsupportedOperationException("You can't create a generic playable.");
    }
  }
}
