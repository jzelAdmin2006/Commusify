package tech.bison.trainee2021.userInterface.command;

import java.util.List;

public class MissingAuthentification implements Command {

  private final String commandSpelling;

  public MissingAuthentification(String commandSpelling) {
    this.commandSpelling = commandSpelling;
  }

  @Override
  public String execute(List<String> arguments) {
    return String.format("You need to be logged in for the command %s.", commandSpelling);
  }

  @Override
  public String getArgumentDescription() {
    return "";
  }

  @Override
  public boolean loginIsRequired() {
    return false;
  }
}
