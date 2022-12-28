package tech.bison.trainee2021.userInterface.command;

import java.util.List;

public class MissingAuthentification implements Command {

  @Override
  public String execute(List<String> arguments) {
    return "You need to be logged in for this command.";
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
