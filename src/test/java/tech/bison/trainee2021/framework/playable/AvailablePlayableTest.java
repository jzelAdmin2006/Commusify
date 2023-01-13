package tech.bison.trainee2021.framework.playable;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tech.bison.trainee2021.Commusify;
import tech.bison.trainee2021.framework.structure.Artist;
import tech.bison.trainee2021.framework.structure.Genre;
import tech.bison.trainee2021.framework.structure.User;

public class AvailablePlayableTest {

  @BeforeEach
  void resetDatabase() {
    Commusify.reset();
  }

  @Test
  void newNormalPlayable_isAvailable_isTrue() {
    Track track = new Track("Title", TrackTest.sampleAudio1, new Genre("Genre"), Collections.singletonList(new Artist(
        Collections.singletonList(new User("UserName", "Password", "FirstName", "LastName", "email.email@email.com")),
        "Artist")));

    boolean result = track.isAvailable();

    assertThat(result).isTrue();
  }
}
