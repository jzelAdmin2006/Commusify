package tech.bison.trainee2021.playable;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import tech.bison.trainee2021.Genre;

public class TrackTest {
  private static byte[] realSampleAudio;
  private static byte[] sampleAudio1 = { 1, 2, 3 };
  private static byte[] sampleAudio2 = { 1, 2, 4 };

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

  @Test
  void newTrackWithTitle_getTitle_isEqual() {
    Track track = new Track("TrackTitleXYZ", sampleAudio1, new Genre("GenreXYZ"));

    String result = track.getTitle();

    assertThat(result).isEqualTo("TrackTitleXYZ");
  }

  @Test
  void newTrackWithDifferentTitle_getTitle_isEqual() {
    Track track = new Track("TrackTitleXYZ2", sampleAudio1, new Genre("GenreXYZ"));

    String result = track.getTitle();

    assertThat(result).isEqualTo("TrackTitleXYZ2");
  }

  @Test
  void newTrackWithAudio_getAudio_isEqual() {
    Track track = new Track("TrackTitleXYZ", sampleAudio1, new Genre("GenreXYZ"));

    byte[] result = track.getAudio();

    assertThat(result).isEqualTo(sampleAudio1);
  }

  @Test
  void newTrackWithDifferentAudio_getAudio_isEqual() {
    Track track = new Track("TrackTitleXYZ", sampleAudio2, new Genre("GenreXYZ"));

    byte[] result = track.getAudio();

    assertThat(result).isEqualTo(sampleAudio2);
  }

  @Test
  void newTrackWithGenre_getGenre_isEqual() {
    Genre genre = new Genre("GenreXYZ");
    Track track = new Track("TrackTitleXYZ", sampleAudio1, genre);

    Genre result = track.getGenre();

    assertThat(result).isEqualTo(genre);
  }

  @Test
  void newTrackWithDifferentGenre_getGenre_isEqual() {
    Genre genre = new Genre("GenreXYZ2");
    Track track = new Track("TrackTitleXYZ", sampleAudio1, genre);

    Genre result = track.getGenre();

    assertThat(result).isEqualTo(genre);
  }

  @Test
  void newTrack_trackWithSameId_isEqual() {
    Genre genre = new Genre("GenreXYZ");
    Track track = new Track("TrackTitleXYZ", sampleAudio1, genre);
    Track trackWithSameId = new Track(track.getId());

    assertThat(track).isEqualTo(trackWithSameId);
    assertThat(track.getTitle()).isEqualTo(trackWithSameId.getTitle()).isEqualTo("TrackTitleXYZ");
    assertThat(track.getAudio()).isEqualTo(trackWithSameId.getAudio()).isEqualTo(sampleAudio1);
    assertThat(track.getGenre()).isEqualTo(trackWithSameId.getGenre()).isEqualTo(genre);
  }

  @Test
  void newTrackWithRealSampleAudio_trackWithSameId_isEqual() {
    Genre genre = new Genre("GenreXYZ");
    Track track = new Track("TrackTitleXYZ", realSampleAudio, genre);
    Track trackWithSameId = new Track(track.getId());

    assertThat(track).isEqualTo(trackWithSameId);
    assertThat(track.getTitle()).isEqualTo(trackWithSameId.getTitle()).isEqualTo("TrackTitleXYZ");
    assertThat(track.getAudio()).isEqualTo(trackWithSameId.getAudio()).isEqualTo(realSampleAudio);
    assertThat(track.getGenre()).isEqualTo(trackWithSameId.getGenre()).isEqualTo(genre);
  }

  @Test
  void twoDifferentTracks_getIds_areUnique() {
    Track track1 = new Track("TrackTitleXYZ", sampleAudio1, new Genre("GenreXYZ"));
    Track track2 = new Track("TrackTitleXYZ2", sampleAudio2, new Genre("GenreXYZ2"));

    int resultId1 = track1.getId();
    int resultId2 = track2.getId();

    assertThat(resultId1).isNotEqualTo(resultId2);
  }
}
