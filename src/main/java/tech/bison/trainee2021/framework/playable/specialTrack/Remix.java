package tech.bison.trainee2021.framework.playable.specialTrack;

import java.util.Collections;
import java.util.List;

import tech.bison.trainee2021.framework.playable.Track;
import tech.bison.trainee2021.framework.structure.Artist;
import tech.bison.trainee2021.framework.structure.Genre;

public class Remix extends Mashup {

  /**
   * This constructor creates a new remix and writes it into the Commusify database
   * A title isn't necessary here because it will take over the title of the original track at the
   * time of the creation.
   * 
   * @param audio
   *          The audio data of the new remix
   * @param genre
   *          The genre of the new remix
   * @param remixers
   *          All remixers of the new remix
   * @param originalTrack
   *          All original tracks of the new remix
   */
  public Remix(byte[] audio, Genre genre, List<Artist> remixers, Track originalTrack) {
    super(originalTrack.getTitle(), audio, genre, remixers, Collections.singletonList(originalTrack));
  }

  /**
   * This constructor reads the existing remix with the given ID from the Commusify
   * database
   * 
   * @param id
   *          The ID of the existing remix
   */
  public Remix(int id) {
    super(id);
  }
}
