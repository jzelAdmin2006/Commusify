package tech.bison.trainee2021.userInterface.command.argumentExpectation;

import java.util.List;

public interface ExactArgumentAmountExpectation extends Expectation {

  public abstract int getValidNumberOfArguments();

  @Override
  public default boolean conditionIsMet(List<String> arguments) {
    return arguments.size() == getValidNumberOfArguments();
  }

  @Override
  public default String getFailedMessage() {
    return String.format("Exactly %s arguments are expected.", getValidNumberOfArguments());
  }
}
