package tech.bison.trainee2021.playable.specialPlaylist.albumType.recordType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tech.bison.trainee2021.Commusify;
import tech.bison.trainee2021.playable.Track;
import tech.bison.trainee2021.playable.TrackTest;
import tech.bison.trainee2021.structure.Artist;
import tech.bison.trainee2021.structure.Genre;
import tech.bison.trainee2021.structure.User;

public class DoubleLongPlayTest {
  private static int doubleLongPlayTrackLimit = LongPlay.LONGPLAY_TRACK_LIMIT * 2;

  @BeforeEach
  void resetDatabase() {
    Commusify.reset();
  }

  @Test
  void newDoubleLongPlay_addTracks_containsTracks() {
    List<Track> tracks = new ArrayList<>();
    tracks.add(new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreDesignationXYZ")));
    tracks.add(new Track("TrackTitleXYZ2", TrackTest.sampleAudio2, new Genre("GenreDesignationXYZ2")));
    List<User> artistMembers = new ArrayList<>();
    artistMembers.add(new User("userNameXYZ", "PasswordXYZ", "FirstNameXYZ", "LastNameXYZ", "email@xyz.com"));
    List<Artist> interpreters = new ArrayList<>();
    interpreters.add(new Artist(artistMembers, "ArtistNameXYZ"));
    DoubleLongPlay longPlay = new DoubleLongPlay("TitleXYZ2", tracks, interpreters);

    List<Track> result = longPlay.getTracks();

    assertThat(result).containsExactlyElementsOf(tracks);
  }

  @Test
  void newDoubleLongPlay_addMaxAmountOfTracks_containsTracks() {
    List<Track> tracks = new ArrayList<>();
    for (int i = 0; i < doubleLongPlayTrackLimit; i++) {
      tracks.add(new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreDesignationXYZ")));
    }
    List<User> artistMembers = new ArrayList<>();
    artistMembers.add(new User("userNameXYZ", "PasswordXYZ", "FirstNameXYZ", "LastNameXYZ", "email@xyz.com"));
    List<Artist> interpreters = new ArrayList<>();
    interpreters.add(new Artist(artistMembers, "ArtistNameXYZ"));
    DoubleLongPlay longPlay = new DoubleLongPlay("TitleXYZ2", tracks, interpreters);

    List<Track> result = longPlay.getTracks();

    assertThat(result).containsExactlyElementsOf(tracks);
  }

  @Test
  void newDoubleLongPlay_addTooManyTracks_cannotAddTracks() {
    List<User> artistMembers = new ArrayList<>();
    artistMembers.add(new User("userNameXYZ", "PasswordXYZ", "FirstNameXYZ", "LastNameXYZ", "email@xyz.com"));
    List<Artist> interpreters = new ArrayList<>();
    interpreters.add(new Artist(artistMembers, "ArtistNameXYZ"));

    List<Track> tracks = new ArrayList<>();
    for (int i = 0; i < doubleLongPlayTrackLimit + 1; i++) {
      tracks.add(new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreDesignationXYZ")));
    }

    assertThatThrownBy(() -> new DoubleLongPlay("TitleXYZ2", tracks, interpreters))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(String.format("Records of type DoubleLongPlay can't contain more than %s track(s).",
            doubleLongPlayTrackLimit));
  }
}
