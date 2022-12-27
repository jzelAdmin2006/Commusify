package tech.bison.trainee2021.userInterface.command;

public class CommandNotFound implements Command {

  private static final String message = "This command wasn't found.";

  @Override
  public String execute(String[] arguments) {
    return message;
  }
}
