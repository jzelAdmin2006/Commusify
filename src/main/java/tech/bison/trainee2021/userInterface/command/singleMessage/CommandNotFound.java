package tech.bison.trainee2021.userInterface.command.singleMessage;

public class CommandNotFound extends SingleMessage {

  public CommandNotFound(String commandSpelling) {
    super(String.format("The command with the spelling \"%s\" wasn't found.", commandSpelling));
  }

  @Override
  public String getArgumentDescription() {
    return "";
  }
}
