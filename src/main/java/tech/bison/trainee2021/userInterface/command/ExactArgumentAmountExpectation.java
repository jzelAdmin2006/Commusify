package tech.bison.trainee2021.userInterface.command;

import java.util.List;

public abstract class ExactArgumentAmountExpectation extends ArgumentExpectation {

  public abstract int getValidNumberOfArguments();

  @Override
  protected boolean isValid(List<String> arguments) {
    return arguments.size() == getValidNumberOfArguments();
  }

  @Override
  protected String getInvalidArgumentsMessage() {
    return String.format("Exactly %s arguments are expected.", getValidNumberOfArguments());
  }
}
