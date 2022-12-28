package tech.bison.trainee2021.userInterface.command.argumentExpectation;

import java.util.List;

public abstract class MinimumArgumentAmountExpectation extends ArgumentExpectation {

  public abstract int getMinimumNumberOfArguments();

  @Override
  protected boolean isValid(List<String> arguments) {
    return arguments.size() >= getMinimumNumberOfArguments();
  }

  @Override
  protected String getInvalidArgumentsMessage() {
    return String.format("At least %s arguments are expected.", getMinimumNumberOfArguments());
  }
}
