package tech.bison.trainee2021.playable.specialTrack;

import java.util.List;

import tech.bison.trainee2021.playable.Track;
import tech.bison.trainee2021.structure.Artist;
import tech.bison.trainee2021.structure.Genre;

public class Remix extends Track {

  public Remix(String title, byte[] audio, Genre genre, List<Artist> interpreters) {
    super(title, audio, genre, interpreters);
    // TODO Auto-generated constructor stub
  }

  // TODO use super.create and add extra table for remixes with foreign key referencing the track
  // table
}
