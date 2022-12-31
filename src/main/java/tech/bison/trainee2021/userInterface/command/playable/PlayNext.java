package tech.bison.trainee2021.userInterface.command.playable;

import java.util.List;

import tech.bison.trainee2021.userInterface.UserInterface;
import tech.bison.trainee2021.userInterface.command.expectation.UserInterfacePlayableAvailableExpectation;

public class PlayNext implements UserInterfacePlayableAvailableExpectation {

  @Override
  public String proceed(List<String> arguments) {
    return UserInterface.getCurrentPlayable().playNext();
  }
}
