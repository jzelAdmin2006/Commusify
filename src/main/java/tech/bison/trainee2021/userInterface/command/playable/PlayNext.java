package tech.bison.trainee2021.userInterface.command.playable;

import java.util.List;

import tech.bison.trainee2021.playable.Playable;
import tech.bison.trainee2021.userInterface.UserInterface;
import tech.bison.trainee2021.userInterface.command.argumentExpectation.NoArgumentExpectation;

public class PlayNext implements NoArgumentExpectation {

  @Override
  public String proceed(List<String> arguments) {
    Playable currentPlayable = UserInterface.getCurrentPlayable();
    if (currentPlayable.isAvailable()) {
      return currentPlayable.playNext();
    } else {
      return "There's nothing playing right now.";
    }
  }

  @Override
  public boolean loginIsRequired() {
    return true;
  }
}
