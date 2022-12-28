package tech.bison.trainee2021.userInterface.command;

import java.util.ArrayList;
import java.util.List;

import tech.bison.trainee2021.structure.Artist;
import tech.bison.trainee2021.structure.User;
import tech.bison.trainee2021.userInterface.UserInterface;
import tech.bison.trainee2021.userInterface.command.argumentExpectation.MinimumArgumentAmountExpectation;

public class SignArtist extends MinimumArgumentAmountExpectation {

  @Override
  public String getArgumentDescription() {
    return "[ArtistName] {[Optional: UserName of artist co-member] [Optional: UserName of artist co-member] [Optional: UserName of artist co-member] ...}";
  }

  @Override
  public int getMinimumNumberOfArguments() {
    return 1;
  }

  @Override
  protected String proceed(List<String> arguments) {
    List<User> members = new ArrayList<>();
    List<String> invalidUserNames = new ArrayList<>();
    members.add(UserInterface.getCurrentUser());
    for (String userName : arguments.subList(1, arguments.size())) {
      if (User.userNameExists(userName)) {
        members.add(new User(userName));
      } else {
        invalidUserNames.add(userName);
      }
    }
    if (invalidUserNames.isEmpty()) {
      Artist artist = new Artist(members, arguments.get(0));
      return String.format("The artist was signed and received the id %s.", artist.getId());
    } else {
      String message = "The following username(s) is / are invalid: ";
      for (String invalidUserName : invalidUserNames) {
        message += "\n" + invalidUserName;
      }
      return message;
    }
  }
}
