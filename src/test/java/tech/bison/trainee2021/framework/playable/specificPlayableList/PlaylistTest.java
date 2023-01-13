package tech.bison.trainee2021.framework.playable.specificPlayableList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tech.bison.trainee2021.Commusify;
import tech.bison.trainee2021.framework.playable.PlayableList;
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
  void newPlaylist_playlistWithSameId_isEqual() {
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

  @Test
  void newPlaylistAsPlayableList_addTrackAsPlayable_trackIsInPlaylist() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    Track track1 = new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter);
    tracks.add(track1);
    PlayableList playlist = new Playlist("TitleXYZ", tracks);

    Track track2 = new Track("TitleXYZ2", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter);
    playlist.addPlayable(track2);

    assertThat(playlist.getPlayables()).containsExactly(track1, track2);
  }

  @Test
  void newPlaylistAsPlayableList_addPlaylist_isNotAllowed() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    PlayableList playlist = new Playlist("TitleXYZ", tracks);

    assertThatThrownBy(() -> playlist.addPlayable(new Playlist("TitleXYZ2", tracks)))
        .isInstanceOf(UnsupportedOperationException.class);
  }
}
