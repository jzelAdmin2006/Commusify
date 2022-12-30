package tech.bison.trainee2021.userInterface.command.create;

import java.util.List;

import tech.bison.trainee2021.framework.playable.Track;
import tech.bison.trainee2021.framework.playable.specificPlayableList.albumType.MixTape;
import tech.bison.trainee2021.framework.structure.Artist;

public class CreateMixTapeAlbum extends CreateRecordAlbum {

  @Override
  protected String createAlbum(List<String> arguments, List<Track> track, List<Artist> artist) {
    MixTape mixTape = new MixTape(arguments.get(0), track, artist, arguments.get(3));
    return String.format("MixTape was created with ID %s.", mixTape.getId());
  }

  @Override
  public String getArgumentDescription() {
    return "[title] [description] [initializer track ID] [ID of interpreter artist which has you as a member] {[Optional: co-interpreter artist ID], [Optional: co-interpreter artist ID], ...}";
  }
}
