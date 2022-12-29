package tech.bison.trainee2021.userInterface.command.create;

import java.util.ArrayList;
import java.util.List;

import tech.bison.trainee2021.playable.PlayableList;
import tech.bison.trainee2021.userInterface.command.argumentExpectation.ExactArgumentAmountExpectation;

public class CreatePlayableList implements ExactArgumentAmountExpectation {

  @Override
  public String proceed(List<String> arguments) {
    PlayableList playableList = new PlayableList(arguments.get(0), new ArrayList<>());
    return String.format("The playable list was created an received the id %s.", playableList.getId());
  }

  @Override
  public String getArgumentDescription() {
    return "[Playable list title]";
  }

  @Override
  public int getValidNumberOfArguments() {
    return 1;
  }
}
