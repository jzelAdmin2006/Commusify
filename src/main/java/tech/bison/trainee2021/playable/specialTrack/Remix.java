package tech.bison.trainee2021.playable.specialTrack;

import tech.bison.trainee2021.Genre;
import tech.bison.trainee2021.playable.Track;

public class Remix extends Track {

  public Remix(String title, byte[] audio, Genre genre) {
    super(title, audio, genre);
    // TODO Auto-generated constructor stub
  }

  // TODO use super.create and add extra table for remixes with foreign key referencing the track
  // table
}
