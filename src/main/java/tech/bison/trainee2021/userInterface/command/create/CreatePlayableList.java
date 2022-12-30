package tech.bison.trainee2021.userInterface.command.create;

import java.util.Collections;
import java.util.List;

import tech.bison.trainee2021.Commusify;
import tech.bison.trainee2021.framework.playable.Playable;
import tech.bison.trainee2021.framework.playable.PlayableList;
import tech.bison.trainee2021.framework.playable.Playable.PlayableSearcher.KnownPlayable;
import tech.bison.trainee2021.userInterface.command.AddPlayableToPlayableList;

public class CreatePlayableList extends AddPlayableToPlayableList {

  protected List<String> arguments;

  @Override
  public String proceed(List<String> arguments) {
    this.arguments = arguments;
    return super.processNextArgument(arguments, Commusify.INVALID_ID);
  }

  @Override
  protected String addPlayableWhenArgumentsChecked(int playableListId, int playableId, KnownPlayable type) {
    return super.checkNextArgument(playableId, type);
  }

  @Override
  protected String addPlayable(Playable playable) {
    List<Playable> initializerPlayable = Collections.singletonList(playable);
    return createPlayableList(initializerPlayable);
  }

  public String createPlayableList(List<Playable> initializerPlayable) {
    PlayableList playableList = new PlayableList(arguments.get(0), initializerPlayable);
    return String.format("The playable list was created an received the id %s.", playableList.getId());
  }

  @Override
  protected String getDescriptionOfFirstArgument() {
    return "Playable list title";
  }
}
