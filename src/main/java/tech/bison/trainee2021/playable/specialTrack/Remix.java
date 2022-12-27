package tech.bison.trainee2021.playable.specialTrack;

import java.util.Collections;
import java.util.List;

import tech.bison.trainee2021.playable.Track;
import tech.bison.trainee2021.structure.Artist;
import tech.bison.trainee2021.structure.Genre;

public class Remix extends Mashup {

  public Remix(String title, byte[] audio, Genre genre, List<Artist> remixers, Track originalTrack) {
    super(title, audio, genre, remixers, Collections.singletonList(originalTrack));
  }

  public Remix(int id) {
    super(id);
  }
}
