package tech.bison.trainee2021.userInterface.command.create;

import java.util.List;
import java.util.regex.Pattern;

import tech.bison.trainee2021.structure.User;
import tech.bison.trainee2021.userInterface.UserInterface;
import tech.bison.trainee2021.userInterface.command.ExactArgumentAmountExpectation;

public class Register extends ExactArgumentAmountExpectation {

  @Override
  public String getArgumentDescription() {
    return "[UserName] [Password] [FirstName] [LastName] [Email]";
  }

  @Override
  public int getValidNumberOfArguments() {
    return 5;
  }

  @Override
  protected String proceed(List<String> arguments) {
    String email = arguments.get(4);
    if (emailIsValid(email)) {
      String password = arguments.get(1);
      User user = new User(arguments.get(0), password, arguments.get(2), arguments.get(3), email);
      user.login(password);
      UserInterface.setCurrentUser(user);
      return String.format("Your user was created with the ID %s. You are now logged in.", user.getId());
    } else {
      return "The email you entered is invalid.";
    }
  }

  private boolean emailIsValid(String email) {
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
        + "A-Z]{2,7}$";

    Pattern pat = Pattern.compile(emailRegex);
    if (email == null)
      return false;
    return pat.matcher(email).matches();
  }
}
