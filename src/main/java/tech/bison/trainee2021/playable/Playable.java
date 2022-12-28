package tech.bison.trainee2021.playable;

import java.util.ArrayList;
import java.util.List;

import tech.bison.trainee2021.playable.PlayableList.PlayableListSearcher;
import tech.bison.trainee2021.playable.Track.TrackSearcher;
import tech.bison.trainee2021.userInterface.command.search.Searchable;
import tech.bison.trainee2021.userInterface.command.search.Searcher;

public interface Playable extends Searchable {
  public int getId();

  public void play();

  public void playNext();

  public void playPrevious();

  public void shuffle(boolean shuffleIsOn);

  public void loop(boolean loopIsOn);

  public void download();

  public boolean isTrack();

  public static class PlayableSearcher extends Searcher {
    public enum KnownPlayable {
      PLAYABLE_LIST,
      TRACK;

      private List<Searchable> search(String search) {
        switch (this) {
          case PLAYABLE_LIST:
            return new PlayableListSearcher().search(search);
          case TRACK:
            return new TrackSearcher().search(search);
        }
        // should never happen
        throw new UnsupportedOperationException(
            String.format("The searching for the known playable %s isn't implemented", this));
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
    public String getSearchCallSP() {
      throw new UnsupportedOperationException("There is no stored procedure for a playable itself.");
    }

    @Deprecated
    @Override
    public Searchable of(int id) {
      throw new UnsupportedOperationException("You can't create a generic playable.");
    }
  }
}
