package tech.bison.trainee2021.userInterface.command.simpleMessage;

public class CommandNotFound extends SimpleMessage {

  public CommandNotFound(String commandSpelling) {
    super(String.format("The command with the spelling \"%s\" wasn't found.", commandSpelling));
  }
}
