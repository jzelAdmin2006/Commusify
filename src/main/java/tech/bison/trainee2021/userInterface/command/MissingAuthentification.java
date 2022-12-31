package tech.bison.trainee2021.userInterface.command;

import java.util.List;

import tech.bison.trainee2021.userInterface.command.expectation.NoExpectation;

public class MissingAuthentification implements NoExpectation {

  private final String commandSpelling;

  public MissingAuthentification(String commandSpelling) {
    this.commandSpelling = commandSpelling;
  }

  @Override
  public boolean loginIsRequired() {
    return false;
  }

  @Override
  public String proceed(List<String> arguments) {
    return String.format("You need to be logged in for the command %s.", commandSpelling);
  }
}
