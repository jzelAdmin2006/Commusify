package tech.bison.trainee2021.userInterface.command.create;

import static tech.bison.trainee2021.userInterface.util.NumericChecker.isNumeric;

import java.util.List;

import tech.bison.trainee2021.framework.structure.Genre;
import tech.bison.trainee2021.framework.structure.SubGenre;
import tech.bison.trainee2021.framework.structure.Genre.GenreIdChecker;

public class CreateSubGenre extends CreateGenre {

  @Override
  public String proceed(List<String> arguments) {
    String superGenreID = arguments.get(1);
    if (isNumeric(superGenreID)) {
      return createWhenSuperGenreExists(arguments, Integer.parseInt(superGenreID));
    } else {
      return String.format("\"%s\" isn't a genre ID (genre IDs are numbers).", superGenreID);
    }
  }

  private String createWhenSuperGenreExists(List<String> arguments, int superGenreID) {
    if (new GenreIdChecker().exists(superGenreID)) {
      SubGenre subGenre = new SubGenre(arguments.get(0), new Genre(superGenreID));
      return String.format("Subgenre was created with id %s.", subGenre.getId());
    } else {
      return String.format("There was no genre found for the super genre id %s.", superGenreID);
    }
  }

  @Override
  public String getArgumentDescription() {
    return String.format("%s [super genre ID]", super.getArgumentDescription());
  }

  @Override
  public int getValidNumberOfArguments() {
    return 2;
  }
}
