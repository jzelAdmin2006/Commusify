package tech.bison.trainee2021.userInterface.command.playable;

import tech.bison.trainee2021.playable.Playable;

public class Play extends Download {
  @Override
  public String processPlayable(Playable playable) {
    playable.play();
    return "Playable is now playing.";
  }
}
