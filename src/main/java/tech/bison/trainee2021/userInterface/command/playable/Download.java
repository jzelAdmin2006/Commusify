package tech.bison.trainee2021.userInterface.command.playable;

import java.util.List;

import tech.bison.trainee2021.playable.Playable;
import tech.bison.trainee2021.playable.Playable.PlayableSearcher.KnownPlayable;
import tech.bison.trainee2021.userInterface.UserInterface;
import tech.bison.trainee2021.userInterface.command.argumentExpectation.ExactArgumentAmountExpectation;

public class Download implements ExactArgumentAmountExpectation {

  @Override
  public String getArgumentDescription() {
    return String.format("[Playable Type (%s)] [Playable ID]", getKnownPlayableTypeSpellings());
  }

  private String getKnownPlayableTypeSpellings() {
    boolean isFirstSpelling = true;
    String spellings = "";
    for (KnownPlayable knownPlayable : KnownPlayable.values()) {
      if (isFirstSpelling) {
        isFirstSpelling = false;
      } else {
        spellings += " / ";
      }
      spellings += knownPlayable.spelling();
    }
    return spellings;
  }

  @Override
  public int getValidNumberOfArguments() {
    return 2;
  }

  @Override
  public String proceed(List<String> arguments) {
    String playableSpelling = arguments.get(0);
    KnownPlayable knownPlayable = KnownPlayable.translate(playableSpelling);
    switch (knownPlayable) {
      case NOT_FOUND:
        return String.format("The playable type \"%s\" couldn't be found.", playableSpelling);
      default:
        String id = arguments.get(1);
        Playable playable = Playable.of(Integer.parseInt(id), knownPlayable);
        if (playable.isAvailable()) {
          UserInterface.setCurrentPlayable(playable);
          return processPlayable(playable);
        } else {
          return String.format("There's no playable with the id %s.", id);
        }
    }
  }

  public String processPlayable(Playable playable) {
    playable.download();
    return "Playable was downloaded.";
  }
}
