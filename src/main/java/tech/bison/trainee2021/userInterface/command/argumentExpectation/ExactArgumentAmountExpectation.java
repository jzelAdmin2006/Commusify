package tech.bison.trainee2021.userInterface.command.argumentExpectation;

import java.util.List;

public interface ExactArgumentAmountExpectation extends ArgumentExpectation {

  public abstract int getValidNumberOfArguments();

  @Override
  public default boolean isValid(List<String> arguments) {
    return arguments.size() == getValidNumberOfArguments();
  }

  @Override
  public default String getInvalidArgumentsMessage() {
    return String.format("Exactly %s arguments are expected.", getValidNumberOfArguments());
  }
}
