package tech.bison.trainee2021.playable;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.jupiter.api.Test;

public class TrackTest {
  private static byte[] sampleAudio = getSampleAudio();

  public static byte[] getSampleAudio() {
    try {
      return download(new URL("https://drive.google.com/u/1/uc?id=10OxDR2mDZiHK370-mBs37bD2mEKwQz-k&export=download"));
    } catch (MalformedURLException e) {
      e.printStackTrace();
      return null;
    }
  }

  private static byte[] download(URL url) {
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
        return data;
      } finally {
        is.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Deprecated // unimplemented
  @Test
  void newTrackWithAudio_getAudio_isCorrect() {
    // Track track = new Track("", sampleAudio, new Genre("House"));
  }
}
