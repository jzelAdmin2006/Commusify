package tech.bison.trainee2021.userInterface.command.create;

import java.util.List;

import tech.bison.trainee2021.structure.Genre;
import tech.bison.trainee2021.userInterface.command.ArgumentExpectation;

public class CreateGenre extends ArgumentExpectation {

  private static final int VALID_NUMBER_OF_ARGUMENTS = 1;

  @Override
  public int getValidNumberOfArguments() {
    return VALID_NUMBER_OF_ARGUMENTS;
  }

  @Override
  protected String proceed(List<String> arguments) {
    Genre genre = new Genre(arguments.get(0));
    return String.format("The genre was created and received the id %s.", genre.getId());
  }
}
