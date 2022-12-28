package tech.bison.trainee2021.userInterface.command.search;

import java.util.Collections;
import java.util.List;

import tech.bison.trainee2021.structure.Genre.GenreSearcher;

public class Search extends ArgumentExpectation {
  private static final int VALID_NUMBER_OF_ARGUMENTS = 2;

  @Override
  public int getValidNumberOfArguments() {
    return VALID_NUMBER_OF_ARGUMENTS;
  }

  public enum KnownSearchable {
    GENRE,
    NOT_FOUND;

    public static KnownSearchable translate(String searchable) {
      for (KnownSearchable knownCommand : KnownSearchable.values()) {
        if (knownCommand != NOT_FOUND && searchable.equals(knownCommand.spelling())) {
          return knownCommand;
        }
      }
      return NOT_FOUND;
    }

    private static final String GENRE_SPELLING = "Genre";
    private static final String NOT_FOUND_SPELLING_MESSAGE = "If your searchable spelling is invalid, the message will tell you.";

    public String spelling() {
      switch (this) {
        case GENRE:
          return GENRE_SPELLING;
        case NOT_FOUND:
          return NOT_FOUND_SPELLING_MESSAGE;
      }
      // should never happen
      throw new UnsupportedOperationException(
          String.format("The spelling for the known searchable %s isn't implemented", this));
    }
  }

  public Searcher create(KnownSearchable searchable) {
    switch (searchable) {
      case GENRE:
        return new GenreSearcher();
      case NOT_FOUND:
        return new NotFoundSearcher();
    }
    // should never happen
    throw new UnsupportedOperationException(String.format("The searchable %s isn't implemented.", searchable));
  }

  @Override
  protected String proceed(List<String> arguments) {
    KnownSearchable knownSearchable = KnownSearchable.translate(arguments.get(0));
    Searcher searcher = create(knownSearchable);
    List<Searchable> searchResult = searcher.search(arguments.get(1));
    String message = "These are your search results:\n";
    for (Searchable searchable : searchResult) {
      message += searchable.result();
    }
    return message;
  }

  public static class NotFoundSearcher implements Searcher {

    @Override
    public List<Searchable> search(String search) {
      return Collections.singletonList(new NotFoundSearchable());
    }
  }

  public static class NotFoundSearchable implements Searchable {

    @Override
    public String result() {
      return String.format("The spelling for this searchable couldn't be recognized.");
    }
  }
}
