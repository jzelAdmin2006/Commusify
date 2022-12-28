package tech.bison.trainee2021.userInterface.command.singleMessage;

import tech.bison.trainee2021.userInterface.UserInterface;
import tech.bison.trainee2021.userInterface.command.CommandFactory;
import tech.bison.trainee2021.userInterface.command.CommandFactory.KnownCommand;

public class ShowAllCommands extends SingleMessage {

  private static final String SPELLING_DELIMITER = "\n\t";

  public ShowAllCommands() {
    super(getCommandSpellings());
  }

  private static String getCommandSpellings() {
    String message = String.format("These are all commands in Commusify:%s%s%s",
        SPELLING_DELIMITER,
        UserInterface.EXIT,
        SPELLING_DELIMITER);
    for (KnownCommand knownCommand : KnownCommand.values()) {
      String spelling = knownCommand.spelling();
      if (!spelling.equals(KnownCommand.NO_ENTRY.spelling())) {
        message += spelling + SPELLING_DELIMITER;
      }
    }
    return message;
  }
}
