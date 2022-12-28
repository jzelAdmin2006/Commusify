package tech.bison.trainee2021.userInterface.command.playable;

import java.util.List;

import tech.bison.trainee2021.playable.Playable;
import tech.bison.trainee2021.playable.Playable.PlayableSearcher.KnownPlayable;
import tech.bison.trainee2021.userInterface.command.argumentExpectation.ExactArgumentAmountExpectation;

public class Play extends ExactArgumentAmountExpectation {

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
        spellings += "/ ";
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
  protected String proceed(List<String> arguments) {
    String playableSpelling = arguments.get(0);
    KnownPlayable knownPlayable = KnownPlayable.translate(playableSpelling);
    switch (knownPlayable) {
      case NOT_FOUND:
        return String.format("The playable type \"%s\" couldn't be found.", playableSpelling);
      case PLAYABLE_LIST:
      case TRACK:
        String id = arguments.get(1);
        Playable.of(Integer.parseInt(id), knownPlayable).play();
        return String.format("Track with id %s is now playing.", id);
    }
    // should never happen
    throw new UnsupportedOperationException(String.format("The known playable %s isn't implemented.", knownPlayable));
  }
}
