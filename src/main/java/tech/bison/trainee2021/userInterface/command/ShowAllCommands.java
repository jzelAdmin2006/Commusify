package tech.bison.trainee2021.userInterface.command;

import tech.bison.trainee2021.userInterface.command.CommandFactory.KnownCommand;

public class ShowAllCommands extends SingleMessage {

  public ShowAllCommands() {
    super(getCommandSpellings());
  }

  private static String getCommandSpellings() {
    String message = "These are all commands in Commusify:\n\n";
    for (KnownCommand knownCommand : KnownCommand.values()) {
      message += knownCommand.spelling() + "\n";
    }
    return message;
  }
}
