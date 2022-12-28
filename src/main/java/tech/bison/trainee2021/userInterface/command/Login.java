package tech.bison.trainee2021.userInterface.command;

import java.util.List;

import tech.bison.trainee2021.structure.User;
import tech.bison.trainee2021.userInterface.command.argumentExpectation.ExactArgumentAmountExpectation;

public class Login extends ExactArgumentAmountExpectation {

  @Override
  public String getArgumentDescription() {
    return "[UserName] [Password]";
  }

  @Override
  public int getValidNumberOfArguments() {
    return 2;
  }

  @Override
  protected String proceed(List<String> arguments) {
    String userName = arguments.get(0);
    if (User.userNameExists(userName)) {
      User user = new User(userName);
      if (user.login(arguments.get(1))) {
        return "You are now logged in.";
      } else {
        return "This is the wrong password.";
      }
    } else {
      return String.format("User with username %s doesn't exist.", userName);
    }
  }

  @Override
  public boolean loginIsRequired() {
    return false;
  }
}
