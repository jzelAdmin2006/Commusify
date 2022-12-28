package tech.bison.trainee2021.userInterface.command;

import tech.bison.trainee2021.userInterface.command.create.CreateGenre;
import tech.bison.trainee2021.userInterface.command.create.Register;
import tech.bison.trainee2021.userInterface.command.search.Search;
import tech.bison.trainee2021.userInterface.command.singleMessage.CommandNotFound;
import tech.bison.trainee2021.userInterface.command.singleMessage.NoEntry;
import tech.bison.trainee2021.userInterface.command.singleMessage.ShowAllCommands;
import tech.bison.trainee2021.userInterface.command.singleMessage.Welcome;

public class CommandFactory {

  public enum KnownCommand {
    CREATE_GENRE,
    WELCOME,
    SEARCH,
    REGISTER,
    LOGIN,
    SIGN_ARTIST,
    SHOW_ALL_COMMANDS,
    NO_ENTRY,
    NOT_FOUND;

    private static final String NOT_FOUND_SPELLING_MESSAGE = "If your command is invalid, the message will tell you.";
    private static final String SEARCH_SPELLING = "/search";
    private static final String CREATE_GENRE_SPELLING = "/createGenre";
    private static final String WELCOME_SPELLING = "/welcome";
    private static final String SHOW_ALL_COMMANDS_SPELLING = "/showAllCommands";
    private static final String REGISTER_SPELLING = "/register";
    private static final String LOGIN_SPELLING = "/login";
    private static final String SIGN_ARTIST_SPELLING = "/signArtist";
    private static final String NO_ENTRY_SPELLING = "";

    public String spelling() {
      switch (this) {
        case CREATE_GENRE:
          return CREATE_GENRE_SPELLING;
        case WELCOME:
          return WELCOME_SPELLING;
        case SEARCH:
          return SEARCH_SPELLING;
        case NO_ENTRY:
          return NO_ENTRY_SPELLING;
        case NOT_FOUND:
          return NOT_FOUND_SPELLING_MESSAGE;
        case SHOW_ALL_COMMANDS:
          return SHOW_ALL_COMMANDS_SPELLING;
        case REGISTER:
          return REGISTER_SPELLING;
        case LOGIN:
          return LOGIN_SPELLING;
        case SIGN_ARTIST:
          return SIGN_ARTIST_SPELLING;
      }
      // should never happen
      throw new UnsupportedOperationException(
          String.format("The spelling for the known command %s isn't implemented", this));
    }

    public static KnownCommand translate(String command) {
      for (KnownCommand knownCommand : KnownCommand.values()) {
        if (knownCommand != NOT_FOUND && command.equals(knownCommand.spelling())) {
          return knownCommand;
        }
      }
      return NOT_FOUND;
    }
  }

  public static Command create(String command) {

    KnownCommand knownCommand = KnownCommand.translate(command);
    switch (knownCommand) {
      case CREATE_GENRE:
        return new CreateGenre();
      case NOT_FOUND:
        return new CommandNotFound(command);
      case NO_ENTRY:
        return new NoEntry();
      case WELCOME:
        return new Welcome();
      case SEARCH:
        return new Search();
      case SHOW_ALL_COMMANDS:
        return new ShowAllCommands();
      case REGISTER:
        return new Register();
      case LOGIN:
        return new Login();
      case SIGN_ARTIST:
        return new SignArtist();
    }
    // should never happen
    throw new UnsupportedOperationException(String.format("The command %s isn't implemented.", command));
  }
}
