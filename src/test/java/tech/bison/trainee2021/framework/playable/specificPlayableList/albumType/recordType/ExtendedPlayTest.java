package tech.bison.trainee2021.framework.playable.specificPlayableList.albumType.recordType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tech.bison.trainee2021.Commusify;
import tech.bison.trainee2021.framework.playable.Playable;
import tech.bison.trainee2021.framework.playable.Track;
import tech.bison.trainee2021.framework.playable.TrackTest;
import tech.bison.trainee2021.framework.structure.Artist;
import tech.bison.trainee2021.framework.structure.Genre;
import tech.bison.trainee2021.framework.structure.User;

public class ExtendedPlayTest {
  @BeforeEach
  void resetDatabase() {
    Commusify.reset();
  }

  @Test
  void newExtendedPlay_addTracks_containsTracks() {
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
    ExtendedPlay extendedPlay = new ExtendedPlay("TitleXYZ2", tracks, interpreters);

    List<Playable> result = extendedPlay.getPlayables();

    assertThat(result).containsExactlyElementsOf(tracks);
  }

  @Test
  void newExtendedPlay_addMaxAmountOfTracks_containsTracks() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ12345", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    for (int i = 0; i < ExtendedPlay.EXTENDED_PLAY_TRACK_LIMIT; i++) {
      tracks.add(new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreDesignationXYZ"), interpreter));
    }
    List<User> artistMembers = new ArrayList<>();
    artistMembers.add(new User("userNameXYZ", "PasswordXYZ", "FirstNameXYZ", "LastNameXYZ", "email@xyz.com"));
    List<Artist> interpreters = new ArrayList<>();
    interpreters.add(new Artist(artistMembers, "ArtistNameXYZ"));
    ExtendedPlay extendedPlay = new ExtendedPlay("TitleXYZ2", tracks, interpreters);

    List<Playable> result = extendedPlay.getPlayables();

    assertThat(result).containsExactlyElementsOf(tracks);
  }

  @Test
  void newExtendedPlay_addTooManyTracks_cannotAddTracks() {
    List<User> artistMembers = new ArrayList<>();
    artistMembers.add(new User("userNameXYZ", "PasswordXYZ", "FirstNameXYZ", "LastNameXYZ", "email@xyz.com"));
    List<Artist> interpreters = new ArrayList<>();
    interpreters.add(new Artist(artistMembers, "ArtistNameXYZ"));

    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ12345", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    for (int i = 0; i < ExtendedPlay.EXTENDED_PLAY_TRACK_LIMIT + 1; i++) {
      tracks.add(new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreDesignationXYZ"), interpreter));
    }

    assertThatThrownBy(() -> new ExtendedPlay("TitleXYZ2", tracks, interpreters))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(String.format("Records of type ExtendedPlay can't contain more than %s track(s).",
            ExtendedPlay.EXTENDED_PLAY_TRACK_LIMIT));
  }

  @Test
  void newExtendedPlay_extendedPlayWithSameId_isEqual() {
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
    ExtendedPlay extendedPlay = new ExtendedPlay("TitleXYZ2", tracks, interpreters);

    ExtendedPlay result = new ExtendedPlay(extendedPlay.getId());

    assertThat(result).isEqualTo(extendedPlay);
  }
}
