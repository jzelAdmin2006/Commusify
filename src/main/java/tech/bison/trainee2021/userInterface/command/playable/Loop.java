package tech.bison.trainee2021.userInterface.command.playable;

import java.util.List;

import tech.bison.trainee2021.userInterface.UserInterface;
import tech.bison.trainee2021.userInterface.command.argumentExpectation.ExactArgumentAmountExpectation;
import tech.bison.trainee2021.userInterface.command.argumentExpectation.UserInterfacePlayableAvailableExpectation;

public class Loop implements ExactArgumentAmountExpectation, UserInterfacePlayableAvailableExpectation {

  private static final String ON = "On";
  private static final String OFF = "Off";

  @Override
  public String proceed(List<String> arguments) {
    String loopStatus = arguments.get(0);
    switch (loopStatus) {
      case ON:
        UserInterface.getCurrentPlayable().loop(true);
        break;
      case OFF:
        UserInterface.getCurrentPlayable().loop(false);
        break;
      default:
        return String.format("The shuffle status \"%s\" is invalid.", loopStatus);
    }
    return String.format("Loop is now %s.", loopStatus);
  }

  @Override
  public String getArgumentDescription() {
    return String.format("[Loop status (%s / %s)]", ON, OFF);
  }

  @Override
  public int getValidNumberOfArguments() {
    return 1;
  }

  @Override
  public boolean conditionIsMet(List<String> arguments) {
    return ExactArgumentAmountExpectation.super.conditionIsMet(arguments)
        && UserInterfacePlayableAvailableExpectation.super.conditionIsMet(arguments);
  }

  @Override
  public String getFailedMessage() {
    return String.format("Can't loop because at least one of these expectations was not met:\n\t%s\n\t%s",
        ExactArgumentAmountExpectation.super.getFailedMessage(),
        UserInterfacePlayableAvailableExpectation.super.getFailedMessage());
  }
}
