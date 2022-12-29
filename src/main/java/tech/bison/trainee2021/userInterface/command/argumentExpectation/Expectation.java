package tech.bison.trainee2021.userInterface.command.argumentExpectation;

import java.util.List;

import tech.bison.trainee2021.userInterface.command.Command;

public interface Expectation extends Command {

  @Override
  public default String execute(List<String> arguments) {
    if (conditionIsMet(arguments)) {
      return proceed(arguments);
    } else {
      return getFailedMessage();
    }
  }

  public String getFailedMessage();

  public boolean conditionIsMet(List<String> arguments);

  public String proceed(List<String> arguments);

  @Override
  public default boolean loginIsRequired() {
    return true;
  }
}
