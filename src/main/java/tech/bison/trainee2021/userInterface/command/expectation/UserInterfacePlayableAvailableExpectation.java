package tech.bison.trainee2021.userInterface.command.expectation;

import java.util.List;

import tech.bison.trainee2021.userInterface.UserInterface;

public interface UserInterfacePlayableAvailableExpectation extends Expectation {

  @Override
  public default String execute(List<String> arguments) {
    if (conditionIsMet(arguments)) {
      return proceed(arguments);
    } else {
      return getFailedMessage();
    }
  }

  @Override
  public default String getFailedMessage() {
    return "Something has to be playing for this command.";
  }

  @Override
  public default boolean conditionIsMet(List<String> arguments) {
    return UserInterface.getCurrentPlayable().isAvailable();
  }

  @Override
  public default String getArgumentDescription() {
    return NoExpectation.NO_ARGUMENT_DESCRIPTION;
  }
}
