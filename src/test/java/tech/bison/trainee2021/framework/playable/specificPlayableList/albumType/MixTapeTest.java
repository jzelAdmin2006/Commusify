package tech.bison.trainee2021.framework.playable.specificPlayableList.albumType;

import static org.assertj.core.api.Assertions.assertThat;

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

public class MixTapeTest {
  @BeforeEach
  void resetDatabase() {
    Commusify.reset();
  }

  @Test
  void newMixTapeWithTitle_getTitle_isTheSame() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ2", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreDesignationXYZ"), interpreter));
    List<User> artistMembers = new ArrayList<>();
    artistMembers.add(new User("userNameXYZ", "PasswordXYZ", "FirstNameXYZ", "LastNameXYZ", "email@xyz.com"));
    List<Artist> interpreters = new ArrayList<>();
    interpreters.add(new Artist(artistMembers, "ArtistNameXYZ"));
    MixTape mixTape = new MixTape("TitleXYZ", tracks, interpreters, "DescriptionXYZ");

    String result = mixTape.getTitle();

    assertThat(result).isEqualTo("TitleXYZ");
  }

  @Test
  void newMixTapeWithDifferentTitle_getTitle_isTheSame() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ2", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreDesignationXYZ"), interpreter));
    List<User> artistMembers = new ArrayList<>();
    artistMembers.add(new User("userNameXYZ", "PasswordXYZ", "FirstNameXYZ", "LastNameXYZ", "email@xyz.com"));
    List<Artist> interpreters = new ArrayList<>();
    interpreters.add(new Artist(artistMembers, "ArtistNameXYZ"));
    MixTape mixTape = new MixTape("TitleXYZ2", tracks, interpreters, "DescriptionXYZ");

    String result = mixTape.getTitle();

    assertThat(result).isEqualTo("TitleXYZ2");
  }

  @Test
  void newMixTapeWithTrack_getTracks_containsTrack() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ2", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreDesignationXYZ"), interpreter));
    List<User> artistMembers = new ArrayList<>();
    artistMembers.add(new User("userNameXYZ", "PasswordXYZ", "FirstNameXYZ", "LastNameXYZ", "email@xyz.com"));
    List<Artist> interpreters = new ArrayList<>();
    interpreters.add(new Artist(artistMembers, "ArtistNameXYZ"));
    MixTape mixTape = new MixTape("TitleXYZ2", tracks, interpreters, "DescriptionXYZ");

    List<Playable> result = mixTape.getPlayables();

    assertThat(result).containsExactlyElementsOf(tracks);
  }

  @Test
  void newMixTapeWithTracks_getTracks_containsTracks() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ2", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreDesignationXYZ"), interpreter));
    tracks.add(new Track("TrackTitleXYZ2", TrackTest.sampleAudio2, new Genre("GenreDesignationXYZ2"), interpreter));
    List<User> artistMembers = new ArrayList<>();
    artistMembers.add(new User("userNameXYZ", "PasswordXYZ", "FirstNameXYZ", "LastNameXYZ", "email@xyz.com"));
    List<Artist> interpreters = new ArrayList<>();
    interpreters.add(new Artist(artistMembers, "ArtistNameXYZ"));
    MixTape mixTape = new MixTape("TitleXYZ2", tracks, interpreters, "DescriptionXYZ");

    List<Playable> result = mixTape.getPlayables();

    assertThat(result).containsExactlyElementsOf(tracks);
  }

  @Test
  void newMixTapeWithInterpreter_getInterpreters_containsInterpreter() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ2", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreDesignationXYZ"), interpreter));
    List<User> artistMembers = new ArrayList<>();
    artistMembers.add(new User("userNameXYZ", "PasswordXYZ", "FirstNameXYZ", "LastNameXYZ", "email@xyz.com"));
    List<Artist> interpreters = new ArrayList<>();
    interpreters.add(new Artist(artistMembers, "ArtistNameXYZ"));
    MixTape mixTape = new MixTape("TitleXYZ2", tracks, interpreters, "DescriptionXYZ");

    List<Artist> result = mixTape.getInterpreters();

    assertThat(result).containsExactlyElementsOf(interpreters);
  }

  @Test
  void newMixTapeWithInterpreters_getInterpreters_containsInterpreters() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ2", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreDesignationXYZ"), interpreter));
    List<User> artistMembers = new ArrayList<>();
    artistMembers.add(new User("userNameXYZ", "PasswordXYZ", "FirstNameXYZ", "LastNameXYZ", "email@xyz.com"));
    List<Artist> interpreters = new ArrayList<>();
    interpreters.add(new Artist(artistMembers, "ArtistNameXYZ"));
    interpreters.add(new Artist(artistMembers, "ArtistNameXYZ2"));
    MixTape mixTape = new MixTape("TitleXYZ2", tracks, interpreters, "DescriptionXYZ");

    List<Artist> result = mixTape.getInterpreters();

    assertThat(result).containsExactlyElementsOf(interpreters);
  }

  @Test
  void newMixTapeWithDescription_getDescription_isTheSame() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ2", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreDesignationXYZ"), interpreter));
    List<User> artistMembers = new ArrayList<>();
    artistMembers.add(new User("userNameXYZ", "PasswordXYZ", "FirstNameXYZ", "LastNameXYZ", "email@xyz.com"));
    List<Artist> interpreters = new ArrayList<>();
    interpreters.add(new Artist(artistMembers, "ArtistNameXYZ"));
    MixTape mixTape = new MixTape("TitleXYZ2", tracks, interpreters, "DescriptionXYZ");

    String result = mixTape.getDescription();

    assertThat(result).isEqualTo("DescriptionXYZ");
  }

  @Test
  void newMixTapeWithDifferentDescription_getDescription_isTheSame() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ2", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreDesignationXYZ"), interpreter));
    List<User> artistMembers = new ArrayList<>();
    artistMembers.add(new User("userNameXYZ", "PasswordXYZ", "FirstNameXYZ", "LastNameXYZ", "email@xyz.com"));
    List<Artist> interpreters = new ArrayList<>();
    interpreters.add(new Artist(artistMembers, "ArtistNameXYZ"));
    MixTape mixTape = new MixTape("TitleXYZ2", tracks, interpreters, "DescriptionXYZ2");

    String result = mixTape.getDescription();

    assertThat(result).isEqualTo("DescriptionXYZ2");
  }

  @Test
  void newMixTape_mixTapeWithSameId_isEqual() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ2", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreDesignationXYZ"), interpreter));
    List<User> artistMembers = new ArrayList<>();
    artistMembers.add(new User("userNameXYZ", "PasswordXYZ", "FirstNameXYZ", "LastNameXYZ", "email@xyz.com"));
    List<Artist> interpreters = new ArrayList<>();
    interpreters.add(new Artist(artistMembers, "ArtistNameXYZ"));
    MixTape mixTape = new MixTape("TitleXYZ2", tracks, interpreters, "DescriptionXYZ");

    MixTape mixTapeWithSameId = new MixTape(mixTape.getId());

    assertThat(mixTape).isEqualTo(mixTapeWithSameId);
    assertThat(mixTape.getId()).isEqualTo(mixTapeWithSameId.getId());
    assertThat(mixTape.getInterpreters()).containsExactlyInAnyOrderElementsOf(mixTapeWithSameId.getInterpreters())
        .containsExactlyElementsOf(interpreters);
    assertThat(mixTape.getTitle()).isEqualTo(mixTapeWithSameId.getTitle()).isEqualTo("TitleXYZ2");
    assertThat(mixTape.getPlayables()).containsExactlyElementsOf(mixTapeWithSameId.getPlayables())
        .containsExactlyElementsOf(tracks);
    assertThat(mixTape.getDescription()).isEqualTo(mixTapeWithSameId.getDescription()).isEqualTo("DescriptionXYZ");
  }

  @Test
  void newDifferentMixTape_mixTapeWithSameId_isEqual() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ2", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TrackTitleXYZ2", TrackTest.sampleAudio1, new Genre("GenreDesignationXYZ"), interpreter));
    List<User> artistMembers = new ArrayList<>();
    artistMembers.add(new User("userNameXY2Z", "PasswordXYZ", "FirstNameXYZ", "LastNameXYZ", "email@xyz.com"));
    List<Artist> interpreters = new ArrayList<>();
    interpreters.add(new Artist(artistMembers, "ArtistNameXYZ"));
    MixTape mixTape = new MixTape("TitleXYZ2", tracks, interpreters, "DescriptionXYZ2");

    MixTape mixTapeWithSameId = new MixTape(mixTape.getId());

    assertThat(mixTape).isEqualTo(mixTapeWithSameId);
    assertThat(mixTape.getId()).isEqualTo(mixTapeWithSameId.getId());
    assertThat(mixTape.getInterpreters()).containsExactlyInAnyOrderElementsOf(mixTapeWithSameId.getInterpreters())
        .containsExactlyElementsOf(interpreters);
    assertThat(mixTape.getTitle()).isEqualTo(mixTapeWithSameId.getTitle()).isEqualTo("TitleXYZ2");
    assertThat(mixTape.getPlayables()).containsExactlyElementsOf(mixTapeWithSameId.getPlayables())
        .containsExactlyElementsOf(tracks);
    assertThat(mixTape.getDescription()).isEqualTo(mixTapeWithSameId.getDescription()).isEqualTo("DescriptionXYZ2");
  }
}
