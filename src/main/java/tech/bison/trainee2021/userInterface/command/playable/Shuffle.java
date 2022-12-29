package tech.bison.trainee2021.userInterface.command.playable;

import java.util.List;

import tech.bison.trainee2021.userInterface.UserInterface;
import tech.bison.trainee2021.userInterface.command.argumentExpectation.ExactArgumentAmountExpectation;
import tech.bison.trainee2021.userInterface.command.argumentExpectation.UserInterfacePlayableAvailableExpectation;

public class Shuffle implements UserInterfacePlayableAvailableExpectation, ExactArgumentAmountExpectation {

  private static final String ON = "On";
  private static final String OFF = "Off";

  @Override
  public String proceed(List<String> arguments) {
    String shuffleStatus = arguments.get(0);
    switch (shuffleStatus) {
      case ON:
        return UserInterface.getCurrentPlayable().shuffle(true);
      case OFF:
        return UserInterface.getCurrentPlayable().shuffle(false);
      default:
        return String.format("The shuffle status \"%s\" is invalid.", shuffleStatus);
    }
  }

  @Override
  public boolean conditionIsMet(List<String> arguments) {
    return ExactArgumentAmountExpectation.super.conditionIsMet(arguments)
        && UserInterfacePlayableAvailableExpectation.super.conditionIsMet(arguments);
  }

  @Override
  public int getValidNumberOfArguments() {
    return 1;
  }

  @Override
  public String getFailedMessage() {
    return String.format("Can't shuffle because at least one of these expectations was not met:\n\t%s\n\t%s",
        ExactArgumentAmountExpectation.super.getFailedMessage(),
        UserInterfacePlayableAvailableExpectation.super.getFailedMessage());
  }

  @Override
  public String getArgumentDescription() {
    return String.format("[Shuffle status (%s / %s)]", ON, OFF);
  }
}
