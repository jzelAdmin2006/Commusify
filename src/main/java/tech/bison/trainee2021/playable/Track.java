package tech.bison.trainee2021.playable;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import tech.bison.trainee2021.Commusify;
import tech.bison.trainee2021.structure.Artist;
import tech.bison.trainee2021.structure.Genre;
import tech.bison.trainee2021.userInterface.command.search.Searchable;
import tech.bison.trainee2021.userInterface.command.search.Searcher;

public class Track implements Playable {
  private static final String MP3_EXTENTION = "mp3";
  private static final String AIFF_EXTENTION = "aiff";
  private static final String AIFF_FIRST_FOUR_BYTES = "70798277";
  private static final String OGG_EXTENTION = "ogg";
  private static final String OGG_FIRST_FOUR_BYTES = "7910310383";
  private static final String AAC_EXTENTION = "aac";
  private static final String AAC_FIRST_FOUR_BYTES = "-1-1580-128";
  private static final String M4A_EXTENTNION = "m4a";
  private static final String M4A_FIRST_FOUR_BYTES = "00028";
  private static final String WAV_EXTENTION = "wav";
  private static final String WAV_FIRST_FOUR_BYTES = "82737070";
  private static final String FLAC_EXTENTION = "flac";
  private static final String FLAC_FIRST_FOUR_BYTES = "102769767";
  private final int id;
  private String title;
  private byte[] audio;
  private Genre genre;
  private final List<Artist> interpreters = new ArrayList<>();
  private boolean repeatIsOn;

  public Track(int id) {
    this.id = id;
    find(id);
  }

  public Track(String title, byte[] audio, Genre genre, List<Artist> interpreters) {
    this.id = create(title, audio, genre, interpreters);
    this.title = title;
    this.audio = audio;
    this.genre = genre;
  }

  private int create(String title, byte[] audio, Genre genre, List<Artist> interpreters) {
    int id = 0;
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall("{call SP_CREATE_TRACK(?, ?, ?)}");
      callableStatement.setString("Title", title);
      callableStatement.setBytes("Audio", audio);
      callableStatement.setInt("GenreID", genre.getId());
      ResultSet result = callableStatement.executeQuery();

      result.next();
      id = result.getInt("ID");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    addInterpreters(id, interpreters);
    return id;
  }

  private void addInterpreters(int id, List<Artist> interpreters) {
    for (Artist interpreter : interpreters) {
      addInterpreter(id, interpreter);
    }
  }

  private void addInterpreter(int id, Artist interpreter) {
    interpreters.add(interpreter);
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall("{call SP_ADD_INTERPRETER(?, ?)}");
      callableStatement.setInt("TrackID", id);
      callableStatement.setInt("InterpreterArtistID", interpreter.getId());
      callableStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void find(int id) {
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall("{call SP_FIND_TRACK(?)}");
      callableStatement.setInt("ID", id);
      ResultSet result = callableStatement.executeQuery();

      result.next();
      title = result.getString("Title");
      audio = result.getBytes("Audio");
      genre = new Genre(result.getInt("FK_GenreID"));

      do {
        interpreters.add(new Artist(result.getInt("FK_ArtistID")));
      } while (result.next());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(audio);
    result = prime * result + Objects.hash(genre, id, title);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Track other = (Track) obj;
    return Arrays.equals(audio, other.audio) && Objects.equals(genre, other.genre) && id == other.id
        && Objects.equals(title, other.title);
  }

  @Override
  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  /**
   * @return copy of the audio data
   */
  public byte[] getAudio() {
    return Arrays.copyOf(audio, audio.length);
  }

  public Genre getGenre() {
    return genre;
  }

  public List<Artist> getInterpreters() {
    return Collections.unmodifiableList(interpreters);
  }

  @Override
  public void play() {
    try {
      String filePath = System.getProperty("java.io.tmpdir") + getFileName();
      saveTo(filePath);
      File file = new File(filePath);
      Desktop.getDesktop().open(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String getFileName() {
    return String.format("/%s%s.%s", getClass().getSimpleName(), id, detectFormat(audio));
  }

  public void saveTo(String filePath) throws FileNotFoundException, IOException {
    FileOutputStream fileOutputStream;
    fileOutputStream = new FileOutputStream(filePath);
    fileOutputStream.write(audio);
    fileOutputStream.close();
  }

  private static String detectFormat(byte[] audio) {
    try {
      String firstFourBytes = "";
      for (int i = 0; i < 4; i++) {
        firstFourBytes += audio[i];
      }
      switch (firstFourBytes) {
        case FLAC_FIRST_FOUR_BYTES:
          return FLAC_EXTENTION;
        case WAV_FIRST_FOUR_BYTES:
          return WAV_EXTENTION;
        case M4A_FIRST_FOUR_BYTES:
          return M4A_EXTENTNION;
        case AAC_FIRST_FOUR_BYTES:
          return AAC_EXTENTION;
        case OGG_FIRST_FOUR_BYTES:
          return OGG_EXTENTION;
        case AIFF_FIRST_FOUR_BYTES:
          return AIFF_EXTENTION;
        default:
          return MP3_EXTENTION;
      }
    } catch (IndexOutOfBoundsException e) {
      return MP3_EXTENTION;
    }
  }

  @Override
  public String playNext() {
    if (repeatIsOn) {
      play();
      return "Loop is on, so the current track is playing again.";
    } else {
      return "You can't play the next track on a single track that's not looping.";
    }
  }

  @Override
  public void playPrevious() {
    play();
  }

  @Override
  public String shuffle(boolean shuffleIsOn) {
    return "You can't shuffle a single track.";
  }

  @Override
  public void loop(boolean loopIsOn) {
    this.repeatIsOn = loopIsOn;
  }

  @Override
  public void download() {
    try {
      saveTo(System.getProperty("user.home") + "/Downloads" + getFileName());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean isTrack() {
    return true;
  }

  @Override
  public String result() {
    return String.format("Track: ID = %s, Title = \"%s\", Genre = \"%s\"", id, title, genre.getDesignation());
  }

  public static class TrackSearcher extends Searcher {
    @Override
    public String getSearchCallSP() {
      return "SP_SEARCH_TRACK";
    }

    @Override
    public Searchable of(int id) {
      return new Track(id);
    }
  }
}
