package tech.bison.trainee2021.userInterface.command.argumentExpectation;

import java.util.List;

import tech.bison.trainee2021.userInterface.command.Command;

public interface ArgumentExpectation extends Command {

  @Override
  public default String execute(List<String> arguments) {
    if (isValid(arguments)) {
      return proceed(arguments);
    } else {
      return getInvalidArgumentsMessage();
    }
  }

  public String getInvalidArgumentsMessage();

  public boolean isValid(List<String> arguments);

  public String proceed(List<String> arguments);

  @Override
  public default boolean loginIsRequired() {
    return true;
  }
}
