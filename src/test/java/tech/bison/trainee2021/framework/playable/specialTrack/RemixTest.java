package tech.bison.trainee2021.framework.playable.specialTrack;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tech.bison.trainee2021.Commusify;
import tech.bison.trainee2021.framework.playable.Track;
import tech.bison.trainee2021.framework.playable.TrackTest;
import tech.bison.trainee2021.framework.structure.Artist;
import tech.bison.trainee2021.framework.structure.Genre;
import tech.bison.trainee2021.framework.structure.User;

public class RemixTest {
  @BeforeEach
  void resetDatabase() {
    Commusify.reset();
  }

  @Test
  void newRemixWithOriginalTrack_getOriginalTrack_isTheSame() {
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    Track originalTrack = new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter);
    Remix remix = new Remix(TrackTest.sampleAudio2, new Genre("GenreXYZ2"), interpreter, originalTrack);

    List<Track> result = remix.getOriginalTracks();

    assertThat(result).containsExactly(originalTrack);
  }

  @Test
  void newRemixWithDifferentOriginalTrack_getOriginalTrack_isTheSame() {
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    Track originalTrack = new Track("TrackTitleXYZ2", TrackTest.sampleAudio2, new Genre("GenreXYZ2"), interpreter);
    Remix remix = new Remix(TrackTest.sampleAudio2, new Genre("GenreXYZ2"), interpreter, originalTrack);

    List<Track> result = remix.getOriginalTracks();

    assertThat(result).containsExactly(originalTrack);
  }
}
