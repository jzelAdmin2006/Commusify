package tech.bison.trainee2021.userInterface.command.create;

import static tech.bison.trainee2021.userInterface.util.NumericChecker.isNumeric;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import tech.bison.trainee2021.playable.Track;
import tech.bison.trainee2021.structure.Artist;
import tech.bison.trainee2021.structure.Genre;
import tech.bison.trainee2021.structure.Genre.GenreIdChecker;
import tech.bison.trainee2021.userInterface.UserInterface;
import tech.bison.trainee2021.userInterface.command.argumentExpectation.MinimumArgumentAmountExpectation;

public class CreateTrack implements MinimumArgumentAmountExpectation {

  @Override
  public String getArgumentDescription() {
    return "[title] [path to audio file] [genre ID] [ID of interpreter artist which has you as a member] {[Optional: co-interpreter artist ID], [Optional: co-interpreter artist ID], ...}";
  }

  @Override
  public int getMinimumNumberOfArguments() {
    return 4;
  }

  @Override
  public String proceed(List<String> arguments) {
    String path = arguments.get(1);
    File file = new File(path);
    if (file.exists()) {
      return processNextArgument(arguments, file);
    } else {
      return String.format("There's no file at the path \"%s\".", path);
    }
  }

  public String processNextArgument(List<String> arguments, File file) {
    String genreId = arguments.get(2);
    if (isNumeric(genreId)) {
      int genreIdValue = Integer.parseInt(genreId);
      if (new GenreIdChecker().exists(genreIdValue)) {
        return processNextArgument(arguments, file, new Genre(genreIdValue));
      } else {
        return String.format("The genre ID \"%s\" doesn't exist.", genreId);
      }
    } else {
      return String.format("\"%s\" is not a genre ID (a genre ID should be a number).", genreId);
    }
  }

  public String processNextArgument(List<String> arguments, File file, Genre genre) {
    List<Artist> interpreters = new ArrayList<>();
    for (String artistId : arguments.subList(3, arguments.size())) {
      if (isNumeric(artistId)) {
        int artistIdValue = Integer.parseInt(artistId);
        if (Artist.idExists(artistIdValue)) {
          interpreters.add(new Artist(artistIdValue));
        } else {
          return String.format("The artist ID \"%s\" doesn't exist.", artistIdValue);
        }
      } else {
        return String.format("\"%s\" is not an artist ID (an artist ID should be a number).", artistId);
      }
    }
    return createTrackWhenAuthorized(arguments, file, genre, interpreters);
  }

  public String createTrackWhenAuthorized(List<String> arguments, File file, Genre genre, List<Artist> interpreters) {
    Artist artistTheUserClaimsToBe = interpreters.get(0);
    if (UserInterface.getCurrentUser().isArtistMember(artistTheUserClaimsToBe)) {
      return createTrack(arguments, file, genre, interpreters);
    } else {
      return String.format("You aren't a member of the artist with the id \"%s\"", artistTheUserClaimsToBe.getId());
    }
  }

  public String createTrack(List<String> arguments, File file, Genre genre, List<Artist> interpreters) {
    try {
      Track track = new Track(arguments.get(0), Files.readAllBytes(file.toPath()), genre, interpreters);
      return String.format("Created new track with ID %s.", track.getId());
    } catch (IOException e) {
      e.printStackTrace();
      return String.format("Unexpectedly couldn't read file with path \"%s\".", file.toPath());
    }
  }
}
