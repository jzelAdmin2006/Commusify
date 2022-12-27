package tech.bison.trainee2021.playable.specialPlaylist;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tech.bison.trainee2021.Commusify;
import tech.bison.trainee2021.playable.Track;
import tech.bison.trainee2021.playable.TrackTest;
import tech.bison.trainee2021.playable.specialPlaylist.albumType.MixTape;
import tech.bison.trainee2021.structure.Artist;
import tech.bison.trainee2021.structure.Genre;
import tech.bison.trainee2021.structure.User;

public class AlbumTest {
  @BeforeEach
  void resetDatabase() {
    Commusify.reset();
  }

  @Test
  void newAlbum_getInterpreters_cannotBeModified() {
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    Artist interpreterArtist = new Artist(members, "ArtistNameXYZ");
    interpreter.add(interpreterArtist);
    Track track = new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter);
    Album album = new MixTape("TitleXYZ", Collections.singletonList(track), interpreter, "DescriptionXYZ");

    List<Artist> result = album.getInterpreters();

    assertThatThrownBy(() -> result.add(interpreterArtist)).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> result.addAll(interpreter)).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> result.clear()).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> result.remove(0)).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> result.removeAll(interpreter)).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> result.set(0, interpreterArtist)).isInstanceOf(UnsupportedOperationException.class);
  }
}
