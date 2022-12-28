package tech.bison.trainee2021.userInterface.command;

import java.util.List;

import tech.bison.trainee2021.structure.Genre;

public class CreateGenre implements Command {

  private static final int VALID_NUMBER_OF_ARGUMENTS = 1;
  private static final String INVALID_NUMBER_OF_ARGUMENTS_MESSAGE = String.format("Exactly %s argument is expected!",
      VALID_NUMBER_OF_ARGUMENTS);

  @Override
  public String execute(List<String> arguments) {
    if (arguments.size() == VALID_NUMBER_OF_ARGUMENTS) {
      Genre genre = new Genre(arguments.get(0));
      return String.format("The genre was created and received the id %s.", genre.getId());
    } else {
      return INVALID_NUMBER_OF_ARGUMENTS_MESSAGE;
    }
  }
}
