package tech.bison.trainee2021.framework.playable;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tech.bison.trainee2021.Commusify;
import tech.bison.trainee2021.framework.playable.Playable.PlayableSearcher;
import tech.bison.trainee2021.framework.playable.Playable.PlayableSearcher.KnownPlayable;
import tech.bison.trainee2021.framework.playable.specificPlayableList.Playlist;
import tech.bison.trainee2021.framework.playable.specificPlayableList.albumType.MixTape;
import tech.bison.trainee2021.framework.structure.Artist;
import tech.bison.trainee2021.framework.structure.Genre;
import tech.bison.trainee2021.framework.structure.User;
import tech.bison.trainee2021.userInterface.command.search.Searchable;

public class PlayableTest {
  @BeforeEach
  void resetDatabase() {
    Commusify.reset();
  }

  @Test
  void newTrack_playableOfIdWithTypeTrack_isTheSame() {
    Track track = new Track("Title", TrackTest.sampleAudio1, new Genre("Genre"), Collections.singletonList(new Artist(
        Collections.singletonList(new User("UserName", "Password", "FirstName", "LastName", "email.email@email.com")),
        "Artist")));

    Playable result = Playable.of(track.getId(), KnownPlayable.TRACK);

    assertThat(result).isEqualTo(track);
  }

  @Test
  void newPlayableList_playableOfIdWithTypePlayableList_isTheSame() {
    List<Playable> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    PlayableList playableList = new PlayableList("TitleXYZ", tracks);

    Playable result = Playable.of(playableList.getId(), KnownPlayable.PLAYABLE_LIST);

    assertThat(result).isEqualTo(playableList);
  }

  @Test
  void newPlaylist_playableOfIdWithTypePlaylist_isTheSame() {
    List<Track> tracks = new ArrayList<>();
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    tracks.add(new Track("TitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter));
    Playlist playlist = new Playlist("TitleXYZ", tracks);

    Playable result = Playable.of(playlist.getId(), KnownPlayable.PLAYLIST);

    assertThat(result).isEqualTo(playlist);
  }

  @Test
  void newAlbum_playableOfIdWithTypeAlbum_isTheSame() {
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

    Playable result = Playable.of(mixTape.getId(), KnownPlayable.ALBUM);

    assertThat(result).isEqualTo(mixTape);
  }

  @Test
  void noPlayable_playableOfInvalidIdWithTypeNotFound_isUnavailablePlayable() {
    Playable result = Playable.of(Commusify.INVALID_ID, KnownPlayable.NOT_FOUND);

    assertThat(result).isInstanceOf(UnavailablePlayable.class);
  }

  @Test
  void newPlayableSearcher_searchPlayable_returnsCorrectPlayables() {
    List<Artist> interpreter = Collections.singletonList(new Artist(
        Collections.singletonList(new User("UserName", "Password", "FirstName", "LastName", "email.email@email.com")),
        "Artist"));
    Track track = new Track("Title", TrackTest.sampleAudio1, new Genre("Genre"), interpreter);
    new Track("xyz", TrackTest.sampleAudio1, new Genre("Genre"), interpreter);
    PlayableSearcher playableSearcher = new PlayableSearcher();

    List<Searchable> playables = playableSearcher.search("Title");

    assertThat(playables).containsExactly(track);
  }

  @Test
  void noPlayables_searchPlayable_returnsNoPlayables() {
    PlayableSearcher playableSearcher = new PlayableSearcher();

    List<Searchable> playables = playableSearcher.search("");

    assertThat(playables).isEmpty();
  }

  @Test
  void knownPlayable_getSpellings_containsCorrectSpellings() {
    String result = KnownPlayable.getSpellings();

    assertThat(result).containsSequence(KnownPlayable.ALBUM_SPELLING)
        .containsSequence(KnownPlayable.PLAYABLE_LIST_SPELLING)
        .containsSequence(KnownPlayable.NOT_FOUND_SPELLING_MESSAGE)
        .containsSequence(KnownPlayable.PLAYLIST_SPELLING)
        .containsSequence(KnownPlayable.TRACK_SPELLING);
  }
}
