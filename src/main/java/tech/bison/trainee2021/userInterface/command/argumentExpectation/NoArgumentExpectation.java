package tech.bison.trainee2021.userInterface.command.argumentExpectation;

import java.util.List;

public interface NoArgumentExpectation extends Expectation {

  static final String NO_ARGUMENT_DESCRIPTION = "";

  @Override
  public default String getArgumentDescription() {
    return NO_ARGUMENT_DESCRIPTION;
  }

  @Override
  public default String getFailedMessage() {
    throw new UnsupportedOperationException(
        "If there's no expectation regarding the argument, there can't be a message regarding the invalidity.");
  }

  @Override
  public default boolean conditionIsMet(List<String> arguments) {
    return true;
  }

  @Override
  public default boolean loginIsRequired() {
    return false;
  }
}
