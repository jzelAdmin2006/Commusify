package tech.bison.trainee2021.framework.playable.specificPlayableList.albumType.recordType;

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

public class SingleTest {
  @BeforeEach
  void resetDatabase() {
    Commusify.reset();
  }

  @Test
  void newSingle_addTrack_containsTrack() {
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ12345", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    Track track = new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreDesignationXYZ"), interpreter);
    List<User> artistMembers = new ArrayList<>();
    artistMembers.add(new User("userNameXYZ", "PasswordXYZ", "FirstNameXYZ", "LastNameXYZ", "email@xyz.com"));
    List<Artist> interpreters = new ArrayList<>();
    interpreters.add(new Artist(artistMembers, "ArtistNameXYZ"));
    Single single = new Single("TitleXYZ2", track, interpreters);

    List<Playable> result = single.getPlayables();

    assertThat(result).containsExactly(track);
  }

  @Test
  void newSingle_singleWithSameId_isEqual() {
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ12345", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    List<User> artistMembers = new ArrayList<>();
    artistMembers.add(new User("userNameXYZ", "PasswordXYZ", "FirstNameXYZ", "LastNameXYZ", "email@xyz.com"));
    List<Artist> interpreters = new ArrayList<>();
    Track track = new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreDesignationXYZ"), interpreter);
    interpreters.add(new Artist(artistMembers, "ArtistNameXYZ"));
    Single single = new Single("TitleXYZ2", track, interpreters);

    Single singleWithSameId = new Single(single.getId());

    assertThat(single).isEqualTo(singleWithSameId);
    assertThat(single.getId()).isEqualTo(singleWithSameId.getId());
    assertThat(single.getInterpreters()).containsExactlyInAnyOrderElementsOf(singleWithSameId.getInterpreters())
        .containsExactlyElementsOf(interpreters);
    assertThat(single.getTitle()).isEqualTo(singleWithSameId.getTitle()).isEqualTo("TitleXYZ2");
    assertThat(single.getPlayables()).containsExactlyElementsOf(singleWithSameId.getPlayables()).containsExactly(track);
  }
}
