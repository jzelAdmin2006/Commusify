package tech.bison.trainee2021.userInterface.command;

import java.util.List;

import tech.bison.trainee2021.framework.structure.User;
import tech.bison.trainee2021.userInterface.UserInterface;
import tech.bison.trainee2021.userInterface.command.expectation.ExactArgumentAmountExpectation;

public class Login implements ExactArgumentAmountExpectation {

  @Override
  public String getArgumentDescription() {
    return "[UserName] [Password]";
  }

  @Override
  public int getValidNumberOfArguments() {
    return 2;
  }

  @Override
  public String proceed(List<String> arguments) {
    String userName = arguments.get(0);
    if (User.userNameExists(userName)) {
      User user = new User(userName);
      if (user.login(arguments.get(1))) {
        UserInterface.setCurrentUser(user);
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
