package tech.bison.trainee2021.userInterface.command;

import static tech.bison.trainee2021.userInterface.util.NumericChecker.isNumeric;

import java.util.List;

import tech.bison.trainee2021.framework.playable.Playable;
import tech.bison.trainee2021.framework.playable.PlayableList;
import tech.bison.trainee2021.framework.playable.Playable.PlayableSearcher.KnownPlayable;
import tech.bison.trainee2021.userInterface.command.expectation.ExactArgumentAmountExpectation;

public class AddPlayableToPlayableList implements ExactArgumentAmountExpectation {

  private PlayableList playableList;

  @Override
  public String proceed(List<String> arguments) {
    String playableListId = arguments.get(0);
    if (isNumeric(playableListId)) {
      return processNextArgument(arguments, Integer.parseInt(playableListId));
    } else {
      return String.format("\"%s\" is not a playable list ID (a playable list  ID should be a number).",
          playableListId);
    }
  }

  protected String processNextArgument(List<String> arguments, int playableListId) {
    String playableId = arguments.get(1);
    if (isNumeric(playableId)) {
      return checkNextArgument(arguments, playableListId, Integer.parseInt(playableId));
    } else {
      return String.format("\"%s\" is not a playable ID (a playable ID should be a number).", playableId);
    }
  }

  private String checkNextArgument(List<String> arguments, int playableListId, int playableId) {
    String playableType = arguments.get(2);
    KnownPlayable knownPlayable = KnownPlayable.translate(playableType);
    switch (knownPlayable) {
      case NOT_FOUND:
        return String.format("The playable type \"%s\" is invalid.", playableType);
      default:
        return addPlayableWhenArgumentsChecked(playableListId, playableId, knownPlayable);
    }
  }

  protected String addPlayableWhenArgumentsChecked(int playableListId, int playableId, KnownPlayable type) {
    Playable playableList = Playable.of(playableListId, KnownPlayable.PLAYABLE_LIST);
    if (playableList.isAvailable()) {
      this.playableList = (PlayableList) playableList;
      return checkNextArgument(playableId, type);
    } else {
      return String.format("The playable list ID %s doesn't exist.", playableListId);
    }
  }

  protected String checkNextArgument(int playableId, KnownPlayable type) {
    Playable playable = Playable.of(playableId, type);
    if (playable.isAvailable()) {
      return addPlayable(playable);
    } else {
      return String.format("The playable ID %s doesn't exist.", playableId);
    }
  }

  protected String addPlayable(Playable playable) {
    playableList.addPlayable(playable);
    return "Playable added to playable list";
  }

  @Override
  public String getArgumentDescription() {
    return String
        .format("[%s] [Playable ID] [Playable type (%s)]", getDescriptionOfFirstArgument(), getPlayableSpellings());
  }

  protected String getPlayableSpellings() {
    return KnownPlayable.getSpellings();
  }

  protected String getDescriptionOfFirstArgument() {
    return "Playable list ID";
  }

  @Override
  public int getValidNumberOfArguments() {
    return 3;
  }
}
