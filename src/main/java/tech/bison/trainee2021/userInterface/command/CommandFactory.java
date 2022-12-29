package tech.bison.trainee2021.userInterface.command;

import tech.bison.trainee2021.userInterface.command.create.CreateGenre;
import tech.bison.trainee2021.userInterface.command.create.CreateTrack;
import tech.bison.trainee2021.userInterface.command.create.Register;
import tech.bison.trainee2021.userInterface.command.create.SignArtist;
import tech.bison.trainee2021.userInterface.command.playable.Download;
import tech.bison.trainee2021.userInterface.command.playable.Loop;
import tech.bison.trainee2021.userInterface.command.playable.Play;
import tech.bison.trainee2021.userInterface.command.playable.PlayNext;
import tech.bison.trainee2021.userInterface.command.playable.PlayPrevious;
import tech.bison.trainee2021.userInterface.command.playable.Shuffle;
import tech.bison.trainee2021.userInterface.command.search.Search;
import tech.bison.trainee2021.userInterface.command.simpleMessage.CommandNotFound;
import tech.bison.trainee2021.userInterface.command.simpleMessage.NoEntry;
import tech.bison.trainee2021.userInterface.command.simpleMessage.Welcome;

public class CommandFactory {

  public enum KnownCommand {
    WELCOME,
    SHOW_ALL_COMMANDS,
    REGISTER,
    LOGIN,
    PLAY,
    PLAY_NEXT,
    PLAY_PREVIOUS,
    SHUFFLE,
    DOWNLOAD,
    LOOP,
    SEARCH,
    CREATE_TRACK,
    CREATE_GENRE,
    SIGN_ARTIST,
    NO_ENTRY,
    NOT_FOUND;

    private static final String NOT_FOUND_SPELLING_MESSAGE = "If your command is invalid, the message will tell you.";
    private static final String SEARCH_SPELLING = "/search";
    private static final String CREATE_GENRE_SPELLING = "/createGenre";
    private static final String WELCOME_SPELLING = "/welcome";
    private static final String SHOW_ALL_COMMANDS_SPELLING = "/showAllCommands";
    private static final String REGISTER_SPELLING = "/register";
    private static final String LOGIN_SPELLING = "/login";
    private static final String CREATE_TRACK_SPELLING = "/createTrack";
    private static final String SIGN_ARTIST_SPELLING = "/signArtist";
    private static final String DOWNLOAD_SPELLING = "/download";
    private static final String PLAY_SPELLING = "/play";
    private static final String PLAY_NEXT_SPELLING = "/playNext";
    private static final String PLAY_PREVIOUS_SPELLING = "/playPrevious";
    private static final String SHUFFLE_SPELLING = "/shuffle";
    private static final String LOOP_SPELLING = "/loop";
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
        case CREATE_TRACK:
          return CREATE_TRACK_SPELLING;
        case PLAY:
          return PLAY_SPELLING;
        case PLAY_NEXT:
          return PLAY_NEXT_SPELLING;
        case PLAY_PREVIOUS:
          return PLAY_PREVIOUS_SPELLING;
        case SHUFFLE:
          return SHUFFLE_SPELLING;
        case LOOP:
          return LOOP_SPELLING;
        case DOWNLOAD:
          return DOWNLOAD_SPELLING;
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

  public static Command create(String spelling) {
    KnownCommand knownCommand = KnownCommand.translate(spelling);
    switch (knownCommand) {
      case CREATE_GENRE:
        return new CreateGenre();
      case NOT_FOUND:
        return new CommandNotFound(spelling);
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
      case CREATE_TRACK:
        return new CreateTrack();
      case PLAY:
        return new Play();
      case PLAY_NEXT:
        return new PlayNext();
      case PLAY_PREVIOUS:
        return new PlayPrevious();
      case SHUFFLE:
        return new Shuffle();
      case LOOP:
        return new Loop();
      case DOWNLOAD:
        return new Download();
    }
    // should never happen
    throw new UnsupportedOperationException(String.format("The command %s isn't implemented.", spelling));
  }
}
