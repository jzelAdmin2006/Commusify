package tech.bison.trainee2021.userInterface.command.simpleMessage;

import java.util.List;

import tech.bison.trainee2021.userInterface.command.Command;

public abstract class SimpleMessage implements Command {

  private final String message;

  public SimpleMessage(String message) {
    this.message = message;
  }

  @Override
  public String execute(List<String> arguments) {
    return message;
  }

  @Override
  public boolean loginIsRequired() {
    return false;
  }
}
