package tech.bison.trainee2021.userInterface.command;

import java.util.List;

public class SingleMessage implements Command {

  private final String message;

  public SingleMessage(String message) {
    this.message = message;
  }

  @Override
  public String execute(List<String> arguments) {
    return message;
  }
}
