package tech.bison.trainee2021.userInterface.command;

import java.util.List;

import tech.bison.trainee2021.userInterface.command.argumentExpectation.NoArgumentExpectation;

public class MissingAuthentification extends NoArgumentExpectation {

  private final String commandSpelling;

  public MissingAuthentification(String commandSpelling) {
    this.commandSpelling = commandSpelling;
  }

  @Override
  public boolean loginIsRequired() {
    return false;
  }

  @Override
  protected String proceed(List<String> arguments) {
    return String.format("You need to be logged in for the command %s.", commandSpelling);
  }
}
