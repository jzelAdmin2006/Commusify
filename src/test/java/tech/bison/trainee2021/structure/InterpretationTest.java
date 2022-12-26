package tech.bison.trainee2021;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import tech.bison.trainee2021.playable.Track;
import tech.bison.trainee2021.playable.TrackTest;

public class InterpretationTest {
  @BeforeEach
  void resetDatabase() {
    Commusify.reset();
  }

  @Test
  void newInterpretationWithTrack_getTrack_isTheSame() {
    List<Artist> artists = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserXYZ", "Password1234", "FirstNameXYZ", "LastNameXYZ", "user.email@xyz.com"));
    artists.add(new Artist(members, "ArtistXYZ"));
    Track track = new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"));
    Interpretation interpretation = new Interpretation(artists, track);

    Track result = interpretation.getTrack();

    assertThat(result).isEqualTo(track);
  }

  @Test
  void newInterpretationWithDifferentTrack_getTrack_isTheSame() {
    List<Artist> artists = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserXYZ", "Password1234", "FirstNameXYZ", "LastNameXYZ", "user.email@xyz.com"));
    artists.add(new Artist(members, "ArtistXYZ"));
    Track track = new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"));
    Interpretation interpretation = new Interpretation(artists, track);

    Track result = interpretation.getTrack();

    assertThat(result).isEqualTo(track);
  }

  @Test
  void newInterpretationWithArtist_getArtists_containsArtist() {
    List<Artist> artists = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserXYZ", "Password1234", "FirstNameXYZ", "LastNameXYZ", "user.email@xyz.com"));
    Artist artist = new Artist(members, "ArtistXYZ");
    artists.add(artist);
    Track track = new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"));
    Interpretation interpretation = new Interpretation(artists, track);

    List<Artist> result = interpretation.getArtists();

    assertThat(result).containsExactly(artist);
  }

  @Test
  void newInterpretationWithArtists_getArtists_containsArtists() {
    List<Artist> artists = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserXYZ", "Password1234", "FirstNameXYZ", "LastNameXYZ", "user.email@xyz.com"));
    artists.add(new Artist(members, "ArtistXYZ"));
    artists.add(new Artist(members, "ArtistXYZ2"));
    Track track = new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"));
    Interpretation interpretation = new Interpretation(artists, track);

    List<Artist> result = interpretation.getArtists();

    assertThat(result).containsExactlyElementsOf(artists);
  }

  @Disabled
  @Test
  void newInterpretation_interpretationWithSameId_isEqual() {
    List<Artist> artists = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserXYZ", "Password1234", "FirstNameXYZ", "LastNameXYZ", "user.email@xyz.com"));
    artists.add(new Artist(members, "ArtistXYZ"));
    Track track = new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"));
    Interpretation interpretation = new Interpretation(artists, track);

    Interpretation interpretationWithSameId = new Interpretation(interpretation.getId());

    assertThat(interpretation).isEqualTo(interpretationWithSameId);
    assertThat(interpretation.getArtists()).containsExactlyElementsOf(interpretationWithSameId.getArtists())
        .containsExactlyElementsOf(artists);
    assertThat(interpretation.getTrack()).isEqualTo(interpretationWithSameId.getTrack()).isEqualTo(track);
  }

  @Disabled
  @Test
  void newDifferentInterpretation_interpretationWithSameId_isEqual() {
    List<Artist> artists = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserXYZ2", "Password12234", "FirstNameX2YZ", "LastNam2eXYZ", "user.email@xyz2.com"));
    artists.add(new Artist(members, "ArtistXYZ"));
    Track track = new Track("TitleXY2Z", TrackTest.sampleAudio1, new Genre("GenreXY2Z"));
    Interpretation interpretation = new Interpretation(artists, track);

    Interpretation interpretationWithSameId = new Interpretation(interpretation.getId());

    assertThat(interpretation).isEqualTo(interpretationWithSameId);
    assertThat(interpretation.getArtists()).containsExactlyElementsOf(interpretationWithSameId.getArtists())
        .containsExactlyElementsOf(artists);
    assertThat(interpretation.getTrack()).isEqualTo(interpretationWithSameId.getTrack()).isEqualTo(track);
  }
}
