package tech.bison.trainee2021.userInterface.command.create;

import static tech.bison.trainee2021.userInterface.util.NumericChecker.isNumeric;

import java.util.List;

import tech.bison.trainee2021.playable.Playable;
import tech.bison.trainee2021.playable.Playable.PlayableSearcher.KnownPlayable;
import tech.bison.trainee2021.playable.PlayableList;
import tech.bison.trainee2021.userInterface.command.argumentExpectation.ExactArgumentAmountExpectation;

public class AddPlayableToPlayableList implements ExactArgumentAmountExpectation {

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

  private String processNextArgument(List<String> arguments, int playableListId) {
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
      case PLAYABLE_LIST:
      case TRACK:
        return addPlayableWhenArgumentsChecked(playableListId, playableId, knownPlayable);
    }
    // should never happen
    throw new UnsupportedOperationException(
        String.format("This action is unimplemented for the playable type %s.", knownPlayable));
  }

  private String addPlayableWhenArgumentsChecked(int playableListId, int playableId, KnownPlayable type) {
    Playable playableList = Playable.of(playableListId, KnownPlayable.PLAYABLE_LIST);
    if (playableList.isAvailable()) {
      return checkNextArgument((PlayableList) playableList, playableId, type);
    } else {
      return String.format("The playable list ID %s doesn't exist.", playableListId);
    }
  }

  private String checkNextArgument(PlayableList playableList, int playableId, KnownPlayable type) {
    Playable playable = Playable.of(playableId, type);
    if (playable.isAvailable()) {
      return addPlayable(playableList, playable);
    } else {
      return String.format("The playable ID %s doesn't exist.", playableId);
    }
  }

  private String addPlayable(PlayableList playableList, Playable playable) {
    playableList.addPlayable(playable);
    return "";
  }

  @Override
  public String getArgumentDescription() {
    return String.format("[Playable list ID] [Playable ID] [Playable type (%s)]", KnownPlayable.getSpellings());
  }

  @Override
  public int getValidNumberOfArguments() {
    return 3;
  }
}
