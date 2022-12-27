package tech.bison.trainee2021.playable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tech.bison.trainee2021.Commusify;
import tech.bison.trainee2021.structure.Artist;
import tech.bison.trainee2021.structure.Genre;
import tech.bison.trainee2021.structure.User;

public class TrackTest {
  private static byte[] realSampleAudio;
  public static byte[] sampleAudio1 = { 1, 2, 3 };
  public static byte[] sampleAudio2 = { 1, 2, 4 };

  @BeforeAll
  static void getSampleAudio() {
    try {
      URL url = new URL("https://drive.google.com/u/1/uc?id=10OxDR2mDZiHK370-mBs37bD2mEKwQz-k&export=download");
      try {
        URLConnection uc = url.openConnection();
        int len = uc.getContentLength();
        InputStream is = new BufferedInputStream(uc.getInputStream());
        try {
          byte[] data = new byte[len];
          int offset = 0;
          while (offset < len) {
            int read = is.read(data, offset, data.length - offset);
            if (read < 0) {
              break;
            }
            offset += read;
          }
          if (offset < len) {
            throw new IOException(String.format("Read %d bytes; expected %d", offset, len));
          }
          realSampleAudio = data;
        } finally {
          is.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

  @BeforeEach
  void resetDatabase() {
    Commusify.reset();
  }

  @Test
  void newTrackWithTitle_getTitle_isEqual() {
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    Track track = new Track("TrackTitleXYZ", sampleAudio1, new Genre("GenreXYZ"), interpreter);

    String result = track.getTitle();

    assertThat(result).isEqualTo("TrackTitleXYZ");
  }

  @Test
  void newTrackWithDifferentTitle_getTitle_isEqual() {
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    Track track = new Track("TrackTitleXYZ2", sampleAudio1, new Genre("GenreXYZ"), interpreter);

    String result = track.getTitle();

    assertThat(result).isEqualTo("TrackTitleXYZ2");
  }

  @Test
  void newTrackWithAudio_getAudio_isEqual() {
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    Track track = new Track("TrackTitleXYZ", sampleAudio1, new Genre("GenreXYZ"), interpreter);

    byte[] result = track.getAudio();

    assertThat(result).isEqualTo(sampleAudio1);
  }

  @Test
  void newTrackWithDifferentAudio_getAudio_isEqual() {
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    Track track = new Track("TrackTitleXYZ", sampleAudio2, new Genre("GenreXYZ"), interpreter);

    byte[] result = track.getAudio();

    assertThat(result).isEqualTo(sampleAudio2);
  }

  @Test
  void newTrackWithGenre_getGenre_isEqual() {
    Genre genre = new Genre("GenreXYZ");
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    Track track = new Track("TrackTitleXYZ", sampleAudio1, genre, interpreter);

    Genre result = track.getGenre();

    assertThat(result).isEqualTo(genre);
  }

  @Test
  void newTrackWithDifferentGenre_getGenre_isEqual() {
    Genre genre = new Genre("GenreXYZ2");
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    Track track = new Track("TrackTitleXYZ", sampleAudio1, genre, interpreter);

    Genre result = track.getGenre();

    assertThat(result).isEqualTo(genre);
  }

  @Test
  void newTrack_trackWithSameId_isEqual() {
    Genre genre = new Genre("GenreXYZ");
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    Track track = new Track("TrackTitleXYZ", sampleAudio1, genre, interpreter);
    Track trackWithSameId = new Track(track.getId());

    assertThat(track).isEqualTo(trackWithSameId);
    assertThat(track.getTitle()).isEqualTo(trackWithSameId.getTitle()).isEqualTo("TrackTitleXYZ");
    assertThat(track.getAudio()).isEqualTo(trackWithSameId.getAudio()).isEqualTo(sampleAudio1);
    assertThat(track.getGenre()).isEqualTo(trackWithSameId.getGenre()).isEqualTo(genre);
    assertThat(track.getInterpreters()).containsExactlyElementsOf(trackWithSameId.getInterpreters())
        .containsExactlyElementsOf(interpreter);
  }

  @Test
  void newTrackWithRealSampleAudio_trackWithSameId_isEqual() {
    Genre genre = new Genre("GenreXYZ");
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    Track track = new Track("TrackTitleXYZ", realSampleAudio, genre, interpreter);
    Track trackWithSameId = new Track(track.getId());

    assertThat(track).isEqualTo(trackWithSameId);
    assertThat(track.getTitle()).isEqualTo(trackWithSameId.getTitle()).isEqualTo("TrackTitleXYZ");
    assertThat(track.getAudio()).isEqualTo(trackWithSameId.getAudio()).isEqualTo(realSampleAudio);
    assertThat(track.getGenre()).isEqualTo(trackWithSameId.getGenre()).isEqualTo(genre);
    assertThat(track.getInterpreters()).containsExactlyElementsOf(trackWithSameId.getInterpreters())
        .containsExactlyElementsOf(interpreter);
  }

  @Test
  void twoDifferentTracks_getIds_areUnique() {
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    Track track1 = new Track("TrackTitleXYZ", sampleAudio1, new Genre("GenreXYZ"), interpreter);
    Track track2 = new Track("TrackTitleXYZ2", sampleAudio2, new Genre("GenreXYZ2"), interpreter);

    int resultId1 = track1.getId();
    int resultId2 = track2.getId();

    assertThat(resultId1).isNotEqualTo(resultId2);
  }

  @Test
  void newTrackWithAudio_modifyAudioAndGetAgain_staysTheSame() {
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    Track track = new Track("TrackTitleXYZ", sampleAudio1, new Genre("GenreXYZ"), interpreter);

    track.getAudio()[1] = 100;
    byte[] result = track.getAudio();

    assertThat(result).isEqualTo(sampleAudio1);
  }

  @Test
  void newTrackWithInterpreter_getInterpreter_isTheSame() {
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    Track track = new Track("TrackTitleXYZ", sampleAudio1, new Genre("GenreXYZ"), interpreter);

    List<Artist> result = track.getInterpreters();

    assertThat(result).isEqualTo(interpreter);
  }

  @Test
  void newTrack_getInterpreters_cannotBeModified() {
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    Artist interpreterArtist = new Artist(members, "ArtistNameXYZ");
    interpreter.add(interpreterArtist);
    Track track = new Track("TrackTitleXYZ", sampleAudio1, new Genre("GenreXYZ"), interpreter);

    List<Artist> result = track.getInterpreters();

    assertThatThrownBy(() -> result.add(interpreterArtist)).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> result.addAll(interpreter)).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> result.clear()).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> result.remove(0)).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> result.removeAll(interpreter)).isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> result.set(0, interpreterArtist)).isInstanceOf(UnsupportedOperationException.class);
  }

  @Test
  void tryOutStuff() {
    Genre genre = new Genre("GenreXYZ");
    List<User> members = new ArrayList<>();
    members.add(new User("UserNameXYZ", "PasswordXYZ", "FirstNameXYZ", "lastNameXYZ", "email@xyz.com"));
    List<Artist> interpreter = new ArrayList<>();
    interpreter.add(new Artist(members, "ArtistNameXYZ"));
    Track track = new Track("TrackTitleXYZ", realSampleAudio, genre, interpreter);

    track.download();
  }
}
