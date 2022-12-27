package tech.bison.trainee2021.playable;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tech.bison.trainee2021.Commusify;
import tech.bison.trainee2021.structure.Artist;
import tech.bison.trainee2021.structure.Genre;
import tech.bison.trainee2021.structure.User;

public class PlaylistTest {
  @BeforeEach
  void resetDatabase() {
    Commusify.reset();
  }

  @Test
  void newPlaylistWithTitle_getTitle_isTheSame() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    Playlist playlist = new Playlist("TitleXYZ", tracks);

    String result = playlist.getTitle();

    assertThat(result).isEqualTo("TitleXYZ");
  }

  @Test
  void newPlaylistWithDifferentTitle_getTitle_isTheSame() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    Playlist playlist = new Playlist("TitleXYZ2", tracks);

    String result = playlist.getTitle();

    assertThat(result).isEqualTo("TitleXYZ2");
  }

  @Test
  void newPlaylistWithTrack_getTracks_containsTrack() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    Playlist playlist = new Playlist("TitleXYZ", tracks);

    List<Track> result = playlist.getTracks();

    assertThat(result).containsExactlyElementsOf(tracks);
  }

  @Test
  void newPlaylistWithTracks_getTracks_containsTracks() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    tracks.add(new Track("TitleXYZ2", TrackTest.sampleAudio2, new Genre("GenreXYZ2"), interpreter));
    Playlist playlist = new Playlist("TitleXYZ", tracks);

    List<Track> result = playlist.getTracks();

    assertThat(result).containsExactlyElementsOf(tracks);
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
    assertThat(playlist.getTracks()).isEqualTo(playlistWithSameId.getTracks()).containsExactlyElementsOf(tracks);
  }

  @Test
  void newDifferentPlaylist_playlistWithSameId_isEqual() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    tracks.add(new Track("Tit2leXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ2"), interpreter));
    Playlist playlist = new Playlist("TitleXYZ2", tracks);

    Playlist playlistWithSameId = new Playlist(playlist.getId());

    assertThat(playlist).isEqualTo(playlistWithSameId);
    assertThat(playlist.getTitle()).isEqualTo(playlistWithSameId.getTitle()).isEqualTo("TitleXYZ2");
    assertThat(playlist.getTracks()).isEqualTo(playlistWithSameId.getTracks()).containsExactlyElementsOf(tracks);
  }

  @Test
  void twoDifferentPlaylists_getIds_areUnique() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    tracks.add(new Track("Tit2leXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ2"), interpreter));
    Playlist playlist1 = new Playlist("TitleXYZ2", tracks);

    List<Track> tracks2 = new ArrayList<>();
    tracks.add(new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    Playlist playlist2 = new Playlist("TitleXYZ", tracks2);

    int resultId1 = playlist1.getId();
    int resultId2 = playlist2.getId();

    assertThat(resultId1).isNotEqualTo(resultId2);
  }
}
