package tech.bison.trainee2021.userInterface.command.expectation;

import java.util.List;

public interface MinimumArgumentAmountExpectation extends Expectation {

  public abstract int getMinimumNumberOfArguments();

  @Override
  public default boolean conditionIsMet(List<String> arguments) {
    return arguments.size() >= getMinimumNumberOfArguments();
  }

  @Override
  public default String getFailedMessage() {
    return String.format("At least %s arguments are expected.", getMinimumNumberOfArguments());
  }
}
