package tech.bison.trainee2021.userInterface.command.create;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import tech.bison.trainee2021.playable.Track;
import tech.bison.trainee2021.structure.Artist;
import tech.bison.trainee2021.structure.Genre;
import tech.bison.trainee2021.userInterface.UserInterface;
import tech.bison.trainee2021.userInterface.command.argumentExpectation.MinimumArgumentAmountExpectation;

public class CreateTrack extends MinimumArgumentAmountExpectation {

  @Override
  public String getArgumentDescription() {
    return "[title] [path to audio file] [genre ID] [ID of interpreter artist which has you as a member] {[Optional: co-interpreter artist ID], [Optional: co-interpreter artist ID], ...}";
  }

  @Override
  public int getMinimumNumberOfArguments() {
    return 4;
  }

  @Override
  protected String proceed(List<String> arguments) {
    String path = arguments.get(1);
    File file = new File(path);
    if (file.exists()) {
      String genreId = arguments.get(2);
      try {
        int genreIdValue = Integer.parseInt(genreId);
        if (Genre.idExists(genreIdValue)) {
          List<Artist> interpreters = new ArrayList<>();
          for (String artistId : arguments.subList(3, arguments.size())) {
            try {
              int artistIdValue = Integer.parseInt(artistId);
              if (Artist.idExists(artistIdValue)) {
                interpreters.add(new Artist(artistIdValue));
              } else {
                return String.format("The artist ID \"%s\" doesn't exist.", artistId);
              }
            } catch (NumberFormatException e) {
              return String.format("\"%s\" is not an artist ID (an artist ID should be a number).", artistId);
            }
          }
          Artist artistTheUserClaimsToBe = interpreters.get(0);
          if (UserInterface.getCurrentUser().isArtistMember(artistTheUserClaimsToBe)) {
            try {
              Track track = new Track(arguments.get(0), Files.readAllBytes(file.toPath()), new Genre(genreIdValue),
                  interpreters);
              return String.format("Created new track with ID %s.", track.getId());
            } catch (IOException e) {
              e.printStackTrace();
              return String.format("Unexpectedly couldn't read file with path \"%s\".", path);
            }
          } else {
            return String.format("You aren't a member of the artist with the id \"%s\"",
                artistTheUserClaimsToBe.getId());
          }
        } else {
          return String.format("The genre ID \"%s\" doesn't exist.", genreId);
        }
      } catch (NumberFormatException e) {
        return String.format("\"%s\" is not a genre ID (a genre ID should be a number).", genreId);
      }
    } else {
      return String.format("There's no file at the path \"%s\".", path);
    }
  }
}
