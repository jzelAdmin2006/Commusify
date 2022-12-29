package tech.bison.trainee2021.userInterface.command.argumentExpectation;

import java.util.List;

public abstract class NoArgumentExpectation extends ArgumentExpectation {

  private static final String NO_ARGUMENT_DESCRIPTION = "";

  @Override
  public String getArgumentDescription() {
    return NO_ARGUMENT_DESCRIPTION;
  }

  @Override
  protected String getInvalidArgumentsMessage() {
    throw new UnsupportedOperationException(
        "If there's no expectation regarding the argument, there can't be a message regarding the invalidity.");
  }

  @Override
  protected boolean isValid(List<String> arguments) {
    return true;
  }
}
