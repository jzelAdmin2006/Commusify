package tech.bison.trainee2021.framework.playable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tech.bison.trainee2021.Commusify;
import tech.bison.trainee2021.framework.structure.Artist;
import tech.bison.trainee2021.framework.structure.Genre;
import tech.bison.trainee2021.framework.structure.User;

public class PlayableListTest {
  @BeforeEach
  void resetDatabase() {
    Commusify.reset();
  }

  @Test
  void newPlayableListWithTitle_getTitle_isTheSame() {
    List<Playable> tracks = new ArrayList<>();
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
    List<Playable> tracks = new ArrayList<>();
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
    List<Playable> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    PlayableList playableList = new PlayableList("TitleXYZ", tracks);

    List<Playable> result = playableList.getPlayables();

    assertThat(result).containsExactlyElementsOf(tracks);
  }

  @Test
  void newPlayableListWithTracks_getTracks_containsTracks() {
    List<Playable> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    tracks.add(new Track("TitleXYZ2", TrackTest.sampleAudio2, new Genre("GenreXYZ2"), interpreter));
    PlayableList playableList = new PlayableList("TitleXYZ", tracks);

    List<Playable> result = playableList.getPlayables();

    assertThat(result).containsExactlyElementsOf(tracks);
  }

  @Test
  void newPlayableList_playableListWithSameId_isEqual() {
    List<Playable> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    PlayableList playableList = new PlayableList("TitleXYZ", tracks);

    PlayableList playableListWithSameId = new PlayableList(playableList.getId());

    assertThat(playableList).isEqualTo(playableListWithSameId);
    assertThat(playableList.getTitle()).isEqualTo(playableListWithSameId.getTitle()).isEqualTo("TitleXYZ");
    assertThat(playableList.getPlayables()).isEqualTo(playableListWithSameId.getPlayables())
        .containsExactlyElementsOf(tracks);
  }

  @Test
  void newDifferentPlayableList_playableListWithSameId_isEqual() {
    List<Playable> tracks = new ArrayList<>();
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
    assertThat(playableList.getPlayables()).isEqualTo(playableListWithSameId.getPlayables())
        .containsExactlyElementsOf(tracks);
  }

  @Test
  void twoDifferentPlayableLists_getIds_areUnique() {
    List<Playable> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    tracks.add(new Track("Tit2leXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ2"), interpreter));
    PlayableList playableList1 = new PlayableList("TitleXYZ2", tracks);

    List<Playable> tracks2 = new ArrayList<>();
    tracks.add(new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    PlayableList playableList2 = new PlayableList("TitleXYZ", tracks2);

    int resultId1 = playableList1.getId();
    int resultId2 = playableList2.getId();

    assertThat(resultId1).isNotEqualTo(resultId2);
  }

  @Test
  void newPlayableList_getTracks_cannotBeModified() {
    List<Playable> inputTracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    Track inputTrack = new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter);
    inputTracks.add(inputTrack);
    PlayableList playableList = new PlayableList("TitleXYZ", inputTracks);

    List<Playable> tracks = playableList.getPlayables();

    assertThatThrownBy(() -> tracks.add(inputTrack)).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> tracks.addAll(inputTracks)).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> tracks.clear()).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> tracks.remove(0)).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> tracks.removeAll(tracks)).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> tracks.set(0, inputTrack)).isInstanceOf(UnsupportedOperationException.class);
  }

  @Test
  void newPlayableListWithPlayableList_getPlayables_containslayableList() {
    List<Playable> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    PlayableList playableList = new PlayableList("TitleXYZ", tracks);
    List<Playable> playableLists = Collections.singletonList(playableList);
    PlayableList playableList2 = new PlayableList("TitleXYZ2", playableLists);

    List<Playable> result = playableList2.getPlayables();

    assertThat(result).containsExactlyElementsOf(playableLists);
  }

  @Test
  void newPlayableListWithPlayableLists_getPlayables_containslayableLists() {
    List<Playable> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    PlayableList playableList = new PlayableList("TitleXYZ", tracks);
    PlayableList playableList2 = new PlayableList("TitleXYZ2", tracks);
    List<Playable> playableLists = new ArrayList<>();
    playableLists.add(playableList);
    playableLists.add(playableList2);
    PlayableList playableList3 = new PlayableList("TitleXYZ2", playableLists);

    List<Playable> result = playableList3.getPlayables();

    assertThat(result).containsExactlyElementsOf(playableLists);
  }

  @Test
  void newPlayableListWithPlayableListsAndTracks_playableListWithSameId_isEqual() {
    List<Playable> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    PlayableList playableList = new PlayableList("TitleXYZ", tracks);
    PlayableList playableList2 = new PlayableList("TitleXYZ2", tracks);
    List<Playable> playables = new ArrayList<>();
    playables.add(playableList);
    playables.add(playableList2);
    playables.add(new Track("TitleXYZ2", TrackTest.sampleAudio2, new Genre("GenreXY2Z"), interpreter));
    PlayableList playableList3 = new PlayableList("TitleXYZ2", playables);

    PlayableList result = new PlayableList(playableList3.getId());

    assertThat(result).isEqualTo(playableList3);
  }
}
