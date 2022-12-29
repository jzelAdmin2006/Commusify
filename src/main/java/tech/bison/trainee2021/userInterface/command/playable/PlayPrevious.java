package tech.bison.trainee2021.userInterface.command.playable;

import java.util.List;

import tech.bison.trainee2021.userInterface.UserInterface;
import tech.bison.trainee2021.userInterface.command.argumentExpectation.UserInterfacePlayableAvailableExpectation;

public class PlayPrevious implements UserInterfacePlayableAvailableExpectation {

  @Override
  public String proceed(List<String> arguments) {
    UserInterface.getCurrentPlayable().playPrevious();
    return "Playing previous track now...";
  }
}
