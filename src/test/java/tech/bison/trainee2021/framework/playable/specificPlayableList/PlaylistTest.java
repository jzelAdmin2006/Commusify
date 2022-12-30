package tech.bison.trainee2021.framework.playable.specificPlayableList;

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

public class PlaylistTest {
  @BeforeEach
  void resetDatabase() {
    Commusify.reset();
  }

  @Test
  void newPlayableList_playableListWithSameId_isEqual() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    Playlist playlist = new Playlist("TitleXYZ", tracks);

    Playlist playlistWithSameId = new Playlist(playlist.getId());

    assertThat(playlist).isEqualTo(playlistWithSameId);
    assertThat(playlist.getTitle()).isEqualTo(playlistWithSameId.getTitle()).isEqualTo("TitleXYZ");
    assertThat(playlist.getPlayables()).isEqualTo(playlistWithSameId.getPlayables()).containsExactlyElementsOf(tracks);
  }
}
