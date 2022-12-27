package tech.bison.trainee2021.playable.specificPlayableList.albumType.recordType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tech.bison.trainee2021.Commusify;
import tech.bison.trainee2021.playable.Playable;
import tech.bison.trainee2021.playable.Track;
import tech.bison.trainee2021.playable.TrackTest;
import tech.bison.trainee2021.structure.Artist;
import tech.bison.trainee2021.structure.Genre;
import tech.bison.trainee2021.structure.User;

public class LongPlayTest {
  @BeforeEach
  void resetDatabase() {
    Commusify.reset();
  }

  @Test
  void newLongPlay_addTracks_containsTracks() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ12345", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreDesignationXYZ"), interpreter));
    tracks.add(new Track("TrackTitleXYZ2", TrackTest.sampleAudio2, new Genre("GenreDesignationXYZ2"), interpreter));
    List<User> artistMembers = new ArrayList<>();
    artistMembers.add(new User("userNameXYZ", "PasswordXYZ", "FirstNameXYZ", "LastNameXYZ", "email@xyz.com"));
    List<Artist> interpreters = new ArrayList<>();
    interpreters.add(new Artist(artistMembers, "ArtistNameXYZ"));
    LongPlay longPlay = new LongPlay("TitleXYZ2", tracks, interpreters);

    List<Playable> result = longPlay.getPlayables();

    assertThat(result).containsExactlyElementsOf(tracks);
  }

  @Test
  void newLongPlay_addMaxAmountOfTracks_containsTracks() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ12345", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    for (int i = 0; i < LongPlay.LONGPLAY_TRACK_LIMIT; i++) {
      tracks.add(new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreDesignationXYZ"), interpreter));
    }
    List<User> artistMembers = new ArrayList<>();
    artistMembers.add(new User("userNameXYZ", "PasswordXYZ", "FirstNameXYZ", "LastNameXYZ", "email@xyz.com"));
    List<Artist> interpreters = new ArrayList<>();
    interpreters.add(new Artist(artistMembers, "ArtistNameXYZ"));
    LongPlay longPlay = new LongPlay("TitleXYZ2", tracks, interpreters);

    List<Playable> result = longPlay.getPlayables();

    assertThat(result).containsExactlyElementsOf(tracks);
  }

  @Test
  void newLongPlay_addTooManyTracks_cannotAddTracks() {
    List<User> artistMembers = new ArrayList<>();
    artistMembers.add(new User("userNameXYZ", "PasswordXYZ", "FirstNameXYZ", "LastNameXYZ", "email@xyz.com"));
    List<Artist> interpreters = new ArrayList<>();
    interpreters.add(new Artist(artistMembers, "ArtistNameXYZ"));

    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ12345", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    for (int i = 0; i < LongPlay.LONGPLAY_TRACK_LIMIT + 1; i++) {
      tracks.add(new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreDesignationXYZ"), interpreter));
    }

    assertThatThrownBy(() -> new LongPlay("TitleXYZ2", tracks, interpreters))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(String.format("Records of type LongPlay can't contain more than %s track(s).",
            LongPlay.LONGPLAY_TRACK_LIMIT));
  }
}
