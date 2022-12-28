package tech.bison.trainee2021.userInterface.command.search;

import java.util.List;

import tech.bison.trainee2021.userInterface.command.Command;

public abstract class ArgumentExpectation implements Command {

  @Override
  public String execute(List<String> arguments) {
    if (arguments.size() == getValidNumberOfArguments()) {
      return proceed(arguments);
    } else {
      return invalidNumberOfArgumentsMessage;
    }
  }

  public abstract int getValidNumberOfArguments();

  protected abstract String proceed(List<String> arguments);

  private final String invalidNumberOfArgumentsMessage = String.format("Exactly %s arguments are expected!",
      getValidNumberOfArguments());
}
