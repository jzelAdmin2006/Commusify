package tech.bison.trainee2021.userInterface.command;

import java.util.List;

public abstract class ArgumentExpectation implements Command {

  @Override
  public String execute(List<String> arguments) {
    if (isValid(arguments)) {
      return proceed(arguments);
    } else {
      return getInvalidArgumentsMessage();
    }
  }

  protected abstract String getInvalidArgumentsMessage();

  protected abstract boolean isValid(List<String> arguments);

  protected abstract String proceed(List<String> arguments);
}
