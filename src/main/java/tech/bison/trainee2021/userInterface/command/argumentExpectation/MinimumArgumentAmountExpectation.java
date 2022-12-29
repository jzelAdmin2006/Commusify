package tech.bison.trainee2021.userInterface.command.argumentExpectation;

import java.util.List;

public interface MinimumArgumentAmountExpectation extends ArgumentExpectation {

  public abstract int getMinimumNumberOfArguments();

  @Override
  public default boolean isValid(List<String> arguments) {
    return arguments.size() >= getMinimumNumberOfArguments();
  }

  @Override
  public default String getInvalidArgumentsMessage() {
    return String.format("At least %s arguments are expected.", getMinimumNumberOfArguments());
  }
}
