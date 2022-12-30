package tech.bison.trainee2021.framework.playable.specialTrack;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

public class MashupTest {
  @BeforeEach
  void resetDatabase() {
    Commusify.reset();
  }

  @Test
  void newMashupWithOriginalTrack_getOriginalTracks_isTheSame() {
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    List<Track> originalTracks = new ArrayList<>();
    originalTracks.add(new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    Mashup mashup = new Mashup("TrackTitleXYZ2", TrackTest.sampleAudio2, new Genre("GenreXYZ2"), interpreter,
        originalTracks);

    List<Track> result = mashup.getOriginalTracks();

    assertThat(result).containsExactlyElementsOf(originalTracks);
  }

  @Test
  void newMashupWithOriginalTracks_getOriginalTracks_isTheSame() {
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    List<Track> originalTracks = new ArrayList<>();
    originalTracks.add(new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    originalTracks.add(new Track("TrackTitleXYZ2", TrackTest.sampleAudio1, new Genre("GenreXYZ2"), interpreter));
    Mashup mashup = new Mashup("TrackTitleXYZ2", TrackTest.sampleAudio2, new Genre("GenreXYZ2"), interpreter,
        originalTracks);

    List<Track> result = mashup.getOriginalTracks();

    assertThat(result).containsExactlyElementsOf(originalTracks);
  }

  @Test
  void newMashup_mashupWithSameId_isEqual() {
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    List<Track> originalTracks = new ArrayList<>();
    originalTracks.add(new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    Mashup mashup = new Mashup("TrackTitleXYZ2", TrackTest.sampleAudio2, new Genre("GenreXYZ2"), interpreter,
        originalTracks);

    Mashup mashupWithSameId = new Mashup(mashup.getId());

    assertThat(mashup).isEqualTo(mashupWithSameId);
    assertThat(mashup.getOriginalTracks()).containsExactlyElementsOf(mashupWithSameId.getOriginalTracks())
        .containsExactlyElementsOf(originalTracks);
  }

  @Test
  void newDifferentMashup_mashupWithSameId_isEqual() {
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    List<Track> originalTracks = new ArrayList<>();
    originalTracks.add(new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    originalTracks.add(new Track("TrackTitleXYZ2", TrackTest.sampleAudio1, new Genre("GenreXYZ2"), interpreter));
    Mashup mashup = new Mashup("TrackTitleXYZ2", TrackTest.sampleAudio2, new Genre("GenreXYZ2"), interpreter,
        originalTracks);

    Mashup mashupWithSameId = new Mashup(mashup.getId());

    assertThat(mashup).isEqualTo(mashupWithSameId);
    assertThat(mashup.getOriginalTracks()).containsExactlyElementsOf(mashupWithSameId.getOriginalTracks())
        .containsExactlyElementsOf(originalTracks);
  }

  @Test
  void newMashup_getOriginalTracks_cannotBeModified() {
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    List<Track> originalTracks = new ArrayList<>();
    Track track = new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter);
    originalTracks.add(track);
    Mashup mashup = new Mashup("TrackTitleXYZ2", TrackTest.sampleAudio2, new Genre("GenreXYZ2"), interpreter,
        originalTracks);

    List<Track> result = mashup.getOriginalTracks();

    assertThatThrownBy(() -> result.add(track)).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> result.addAll(originalTracks)).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> result.clear()).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> result.remove(0)).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> result.removeAll(originalTracks)).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> result.set(0, track)).isInstanceOf(UnsupportedOperationException.class);
  }
}
