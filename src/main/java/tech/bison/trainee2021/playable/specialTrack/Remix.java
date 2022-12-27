package tech.bison.trainee2021.playable.specialTrack;

import java.util.List;

import tech.bison.trainee2021.playable.Track;
import tech.bison.trainee2021.structure.Artist;
import tech.bison.trainee2021.structure.Genre;

public class Remix extends Mashup {

  public Remix(String title, byte[] audio, Genre genre, List<Artist> remixers, Track original) {
    super(title, audio, genre, remixers);
  }

  public Remix(int id) {
    super(id);
  }
}
