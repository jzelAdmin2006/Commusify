package tech.bison.trainee2021.userInterface.command.search;

import java.util.Collections;
import java.util.List;

import tech.bison.trainee2021.framework.playable.Playable.PlayableSearcher;
import tech.bison.trainee2021.framework.playable.Track.TrackSearcher;
import tech.bison.trainee2021.framework.structure.Artist.ArtistSearcher;
import tech.bison.trainee2021.framework.structure.Genre.GenreSearcher;
import tech.bison.trainee2021.framework.structure.User.UserSearcher;
import tech.bison.trainee2021.userInterface.command.expectation.ExactArgumentAmountExpectation;

public class Search implements ExactArgumentAmountExpectation {
  private static final int VALID_NUMBER_OF_ARGUMENTS = 2;

  @Override
  public int getValidNumberOfArguments() {
    return VALID_NUMBER_OF_ARGUMENTS;
  }

  public enum KnownSearchable {
    PLAYABLE,
    GENRE,
    ARTIST,
    USER,
    TRACK,
    NOT_FOUND;

    public static KnownSearchable translate(String searchable) {
      for (KnownSearchable knownSearchable : KnownSearchable.values()) {
        if (knownSearchable != NOT_FOUND && searchable.equals(knownSearchable.spelling())) {
          return knownSearchable;
        }
      }
      return NOT_FOUND;
    }

    private static final String GENRE_SPELLING = "Genre";
    private static final String PLAYABLE_SPELLING = "Playable";
    private static final String ARTIST_SPELLING = "Artist";
    private static final String USER_SPELLING = "User";
    private static final String TRACK_SPELLING = "Track";
    private static final String NOT_FOUND_SPELLING_MESSAGE = "If your searchable spelling is invalid, the message will tell you.";

    public String spelling() {
      switch (this) {
        case GENRE:
          return GENRE_SPELLING;
        case PLAYABLE:
          return PLAYABLE_SPELLING;
        case NOT_FOUND:
          return NOT_FOUND_SPELLING_MESSAGE;
        case ARTIST:
          return ARTIST_SPELLING;
        case USER:
          return USER_SPELLING;
        case TRACK:
          return TRACK_SPELLING;
      }
      // should never happen
      throw new UnsupportedOperationException(
          String.format("The spelling for the known searchable %s isn't implemented", this));
    }
  }

  private Searcher create(KnownSearchable searchable) {
    switch (searchable) {
      case GENRE:
        return new GenreSearcher();
      case NOT_FOUND:
        return new NotFoundSearcher();
      case PLAYABLE:
        return new PlayableSearcher();
      case ARTIST:
        return new ArtistSearcher();
      case USER:
        return new UserSearcher();
      case TRACK:
        return new TrackSearcher();
    }
    // should never happen
    throw new UnsupportedOperationException(String.format("The searchable %s isn't implemented.", searchable));
  }

  @Override
  public String proceed(List<String> arguments) {
    KnownSearchable knownSearchable = KnownSearchable.translate(arguments.get(0));
    Searcher searcher = create(knownSearchable);
    List<Searchable> searchResult = searcher.search(arguments.get(1));
    String message = "These are your search results:\n";
    for (Searchable searchable : searchResult) {
      message += searchable.result() + "\n";
    }
    return message;
  }

  public static class NotFoundSearcher extends Searcher {

    @Override
    public List<Searchable> search(String search) {
      return Collections.singletonList(new NotFoundSearchable());
    }

    @Deprecated
    @Override
    public String getSearchCallSP() {
      throw new UnsupportedOperationException("There is no stored procedure for this searcher.");
    }

    @Deprecated
    @Override
    public Searchable of(int id) {
      throw new UnsupportedOperationException("There is no type available for this searcher.");
    }
  }

  public static class NotFoundSearchable implements Searchable {

    @Override
    public String result() {
      return String.format("The spelling for this searchable couldn't be recognized.");
    }
  }

  @Override
  public String getArgumentDescription() {
    String typeSpellings = "";
    boolean isFirstTime = true;
    for (KnownSearchable knownSearchable : KnownSearchable.values()) {
      if (knownSearchable != KnownSearchable.NOT_FOUND) {
        if (isFirstTime) {
          isFirstTime = false;
        } else {
          typeSpellings += " / ";
        }
        typeSpellings += knownSearchable.spelling();
      }
    }
    return String.format("[searchable type: (%s)] [search expression]", typeSpellings);
  }
}
