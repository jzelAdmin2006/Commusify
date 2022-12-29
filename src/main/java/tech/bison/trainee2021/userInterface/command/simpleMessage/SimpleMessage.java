package tech.bison.trainee2021.userInterface.command.simpleMessage;

import java.util.List;

import tech.bison.trainee2021.userInterface.command.argumentExpectation.NoArgumentExpectation;

public class SimpleMessage implements NoArgumentExpectation {
  private final String message;

  public SimpleMessage(String message) {
    this.message = message;
  }

  @Override
  public String proceed(List<String> arguments) {
    return message;
  }
}
