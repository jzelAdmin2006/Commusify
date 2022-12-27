package tech.bison.trainee2021.userInterface.command;

public class CommandNotFound extends SingleMessage {

  public CommandNotFound(String commandSpelling) {
    super(String.format("The command with the spelling \"%s\" wasn't found.", commandSpelling));
  }
}
