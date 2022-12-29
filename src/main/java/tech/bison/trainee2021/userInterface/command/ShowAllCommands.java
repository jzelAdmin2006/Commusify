package tech.bison.trainee2021.userInterface.command;

import java.util.List;

import tech.bison.trainee2021.userInterface.UserInterface;
import tech.bison.trainee2021.userInterface.command.CommandFactory.KnownCommand;
import tech.bison.trainee2021.userInterface.command.argumentExpectation.NoExpectation;

public class ShowAllCommands implements NoExpectation {

  private static final String SPELLING_ARGUMENTDESC_DELIMITER = " ";
  private static final String COMMAND_DELIMITER = "\n\t";

  @Override
  public boolean loginIsRequired() {
    return false;
  }

  @Override
  public String proceed(List<String> arguments) {
    String message = String.format(
        "These are all commands in Commusify (expressions in box brackets represent the arguments):%s%s%s",
        COMMAND_DELIMITER,
        UserInterface.EXIT,
        COMMAND_DELIMITER);
    for (KnownCommand knownCommand : KnownCommand.values()) {
      String spelling = knownCommand.spelling();
      if (knownCommand != KnownCommand.NOT_FOUND && knownCommand != KnownCommand.NO_ENTRY) {
        message += spelling + SPELLING_ARGUMENTDESC_DELIMITER
            + CommandFactory.create(knownCommand.spelling()).getArgumentDescription() + SPELLING_ARGUMENTDESC_DELIMITER
            + COMMAND_DELIMITER;
      }
    }
    return message;
  }
}
