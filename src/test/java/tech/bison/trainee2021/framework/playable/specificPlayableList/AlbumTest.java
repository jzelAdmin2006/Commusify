package tech.bison.trainee2021.framework.playable.specificPlayableList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tech.bison.trainee2021.Commusify;
import tech.bison.trainee2021.framework.playable.Track;
import tech.bison.trainee2021.framework.playable.TrackTest;
import tech.bison.trainee2021.framework.playable.specificPlayableList.albumType.MixTape;
import tech.bison.trainee2021.framework.playable.specificPlayableList.albumType.recordType.LongPlay;
import tech.bison.trainee2021.framework.structure.Artist;
import tech.bison.trainee2021.framework.structure.Genre;
import tech.bison.trainee2021.framework.structure.User;

public class AlbumTest {
  @BeforeEach
  void resetDatabase() {
    Commusify.reset();
  }

  @Test
  void newAlbum_getInterpreters_cannotBeModified() {
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    Artist interpreterArtist = new Artist(members, "ArtistNameXYZ");
    interpreter.add(interpreterArtist);
    Track track = new Track("TrackTitleXYZ", TrackTest.sampleAudio1, new Genre("GenreXYZ"), interpreter);
    Album album = new MixTape("TitleXYZ", Collections.singletonList(track), interpreter, "DescriptionXYZ");

    List<Artist> result = album.getInterpreters();

    assertThatThrownBy(() -> result.add(interpreterArtist)).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> result.addAll(interpreter)).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> result.clear()).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> result.remove(0)).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> result.removeAll(interpreter)).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> result.set(0, interpreterArtist)).isInstanceOf(UnsupportedOperationException.class);
  }

  @Test
  void newAlbumWithTypeMixTape_getAlbumOfId_hasInstanceMixTape() {
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

    Album mixTapeWithSameId = Album.of(mixTape.getId());

    assertThat(mixTapeWithSameId).isInstanceOf(MixTape.class);
  }

  @Test
  void newAlbumWithTypeLongPlay_getAlbumOfId_hasInstanceLongPlay() {
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

    Album longPlayWithSameId = Album.of(longPlay.getId());

    assertThat(longPlayWithSameId).isInstanceOf(LongPlay.class);
  }

  @Test
  void getAlbumOf_idIsInvalid_cannotCreateAlbum() {
    assertThatThrownBy(() -> Album.of(Commusify.INVALID_ID)).isInstanceOf(IllegalArgumentException.class)
        .hasMessage(String.format("The type for the album with the id %s couldn't be found.", Commusify.INVALID_ID));
  }
}
