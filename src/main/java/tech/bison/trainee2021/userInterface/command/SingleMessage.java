package tech.bison.trainee2021.userInterface.command;

public class SingleMessage implements Command {

  private final String message;

  public SingleMessage(String message) {
    this.message = message;
  }

  @Override
  public String execute(String[] arguments) {
    return message;
  }
}
