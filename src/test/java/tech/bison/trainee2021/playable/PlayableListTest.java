package tech.bison.trainee2021.playable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tech.bison.trainee2021.Commusify;
import tech.bison.trainee2021.structure.Artist;
import tech.bison.trainee2021.structure.Genre;
import tech.bison.trainee2021.structure.User;

public class PlayableListTest {
  @BeforeEach
  void resetDatabase() {
    Commusify.reset();
  }

  @Test
  void newPlayableListWithTitle_getTitle_isTheSame() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    PlayableList playableList = new PlayableList("TitleXYZ", tracks);

    String result = playableList.getTitle();

    assertThat(result).isEqualTo("TitleXYZ");
  }

  @Test
  void newPlayableListWithDifferentTitle_getTitle_isTheSame() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    PlayableList playableList = new PlayableList("TitleXYZ2", tracks);

    String result = playableList.getTitle();

    assertThat(result).isEqualTo("TitleXYZ2");
  }

  @Test
  void newPlayableListWithTrack_getTracks_containsTrack() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    PlayableList playableList = new PlayableList("TitleXYZ", tracks);

    List<Track> result = playableList.getTracks();

    assertThat(result).containsExactlyElementsOf(tracks);
  }

  @Test
  void newPlayableListWithTracks_getTracks_containsTracks() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    tracks.add(new Track("TitleXYZ2", TrackTest.sampleAudio2, new Genre("GenreXYZ2"), interpreter));
    PlayableList playableList = new PlayableList("TitleXYZ", tracks);

    List<Track> result = playableList.getTracks();

    assertThat(result).containsExactlyElementsOf(tracks);
  }

  @Test
  void newPlayableList_playableListWithSameId_isEqual() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    PlayableList playableList = new PlayableList("TitleXYZ", tracks);

    PlayableList playableListWithSameId = new PlayableList(playableList.getId());

    assertThat(playableList).isEqualTo(playableListWithSameId);
    assertThat(playableList.getTitle()).isEqualTo(playableListWithSameId.getTitle()).isEqualTo("TitleXYZ");
    assertThat(playableList.getTracks()).isEqualTo(playableListWithSameId.getTracks())
        .containsExactlyElementsOf(tracks);
  }

  @Test
  void newDifferentPlayableList_playableListWithSameId_isEqual() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    tracks.add(new Track("Tit2leXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ2"), interpreter));
    PlayableList playableList = new PlayableList("TitleXYZ2", tracks);

    PlayableList playableListWithSameId = new PlayableList(playableList.getId());

    assertThat(playableList).isEqualTo(playableListWithSameId);
    assertThat(playableList.getTitle()).isEqualTo(playableListWithSameId.getTitle()).isEqualTo("TitleXYZ2");
    assertThat(playableList.getTracks()).isEqualTo(playableListWithSameId.getTracks())
        .containsExactlyElementsOf(tracks);
  }

  @Test
  void twoDifferentPlayableLists_getIds_areUnique() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    tracks.add(new Track("Tit2leXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ2"), interpreter));
    PlayableList playableList1 = new PlayableList("TitleXYZ2", tracks);

    List<Track> tracks2 = new ArrayList<>();
    tracks.add(new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    PlayableList playableList2 = new PlayableList("TitleXYZ", tracks2);

    int resultId1 = playableList1.getId();
    int resultId2 = playableList2.getId();

    assertThat(resultId1).isNotEqualTo(resultId2);
  }

  @Test
  void newPlayableList_getTracks_cannotBeModified() {
    List<Track> inputTracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    Track inputTrack = new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter);
    inputTracks.add(inputTrack);
    PlayableList playableList = new PlayableList("TitleXYZ", inputTracks);

    List<Track> tracks = playableList.getTracks();

    assertThatThrownBy(() -> tracks.add(inputTrack)).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> tracks.addAll(inputTracks)).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> tracks.clear()).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> tracks.remove(0)).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> tracks.removeAll(tracks)).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> tracks.set(0, inputTrack)).isInstanceOf(UnsupportedOperationException.class);
  }
}
