package tech.bison.trainee2021.userInterface.command.singleMessage;

import java.util.List;

import tech.bison.trainee2021.userInterface.command.Command;

public abstract class SingleMessage implements Command {

  private final String message;

  public SingleMessage(String message) {
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
