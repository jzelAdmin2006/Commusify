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

public class SingleTest {
  @BeforeEach
  void resetDatabase() {
    Commusify.reset();
  }

  @Test
  void newSingle_addTrack_containsTrack() {
    List<Track> track = new ArrayList<>();
    track.add(new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreDesignationXYZ")));
    List<User> artistMembers = new ArrayList<>();
    artistMembers.add(new User("userNameXYZ", "PasswordXYZ", "FirstNameXYZ", "LastNameXYZ", "email@xyz.com"));
    List<Artist> interpreters = new ArrayList<>();
    interpreters.add(new Artist(artistMembers, "ArtistNameXYZ"));
    Single single = new Single("TitleXYZ2", track, interpreters);

    List<Track> result = single.getTracks();

    assertThat(result).containsExactlyElementsOf(track);
  }

  @Test
  void newSingle_addTracks_singleCanOnlyContainOneTrack() {
    List<User> artistMembers = new ArrayList<>();
    artistMembers.add(new User("userNameXYZ", "PasswordXYZ", "FirstNameXYZ", "LastNameXYZ", "email@xyz.com"));
    List<Artist> interpreters = new ArrayList<>();
    interpreters.add(new Artist(artistMembers, "ArtistNameXYZ"));

    List<Track> tracks = new ArrayList<>();
    tracks.add(new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreDesignationXYZ")));
    tracks.add(new Track("TrackTitleXYZ2", TrackTest.sampleAudio2, new Genre("GenreDesignationXYZ2")));

    assertThatThrownBy(() -> new Single("TitleXYZ2", tracks, interpreters)).isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Records of type Single can't contain more than 1 track(s).");
  }

  @Test
  void newSingle_mixSingleWithSameId_isEqual() {
    List<Track> tracks = new ArrayList<>();
    tracks.add(new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreDesignationXYZ")));
    List<User> artistMembers = new ArrayList<>();
    artistMembers.add(new User("userNameXYZ", "PasswordXYZ", "FirstNameXYZ", "LastNameXYZ", "email@xyz.com"));
    List<Artist> interpreters = new ArrayList<>();
    interpreters.add(new Artist(artistMembers, "ArtistNameXYZ"));
    Single single = new Single("TitleXYZ2", tracks, interpreters);

    Single singleWithSameId = new Single(single.getId());

    assertThat(single).isEqualTo(singleWithSameId);
    assertThat(single.getId()).isEqualTo(singleWithSameId.getId());
    assertThat(single.getInterpreters()).containsExactlyInAnyOrderElementsOf(singleWithSameId.getInterpreters())
        .containsExactlyElementsOf(interpreters);
    assertThat(single.getTitle()).isEqualTo(singleWithSameId.getTitle()).isEqualTo("TitleXYZ2");
    assertThat(single.getTracks()).containsExactlyElementsOf(singleWithSameId.getTracks())
        .containsExactlyElementsOf(tracks);
  }
}
