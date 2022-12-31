package tech.bison.trainee2021.userInterface.command.simpleMessage;

import java.util.List;

import tech.bison.trainee2021.userInterface.command.expectation.NoExpectation;

public class SimpleMessage implements NoExpectation {
  private final String message;

  public SimpleMessage(String message) {
    this.message = message;
  }

  @Override
  public String proceed(List<String> arguments) {
    return message;
  }
}
