package tech.bison.trainee2021.userInterface.command.create;

import java.util.List;

import tech.bison.trainee2021.framework.structure.Genre;
import tech.bison.trainee2021.userInterface.command.expectation.ExactArgumentAmountExpectation;

public class CreateGenre implements ExactArgumentAmountExpectation {

  @Override
  public int getValidNumberOfArguments() {
    return 1;
  }

  @Override
  public String proceed(List<String> arguments) {
    Genre genre = new Genre(arguments.get(0));
    return String.format("The genre was created and received the id %s.", genre.getId());
  }

  @Override
  public String getArgumentDescription() {
    return "[designation]";
  }
}
