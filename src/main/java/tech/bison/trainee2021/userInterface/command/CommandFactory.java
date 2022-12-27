package tech.bison.trainee2021.userInterface.command;

public class CommandFactory {

  public enum KnownCommand {
    CREATE_GENRE,
    NOT_FOUND;

    private String spelling() {
      switch (this) {
        case CREATE_GENRE:
          return "/createGenre";
        case NOT_FOUND:
          // should never happen
          throw new UnsupportedOperationException("The not found command has no spelling.");
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
        return new CommandNotFound();
    }
    // should never happen
    throw new UnsupportedOperationException(String.format("The command %s isn't implemented.", command));
  }
}
