package tech.bison.trainee2021.playable;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

import tech.bison.trainee2021.Commusify;
import tech.bison.trainee2021.structure.Genre;

public class Track {
  private final int id;
  private String title;
  private byte[] audio;
  private Genre genre;

  public Track(int id) {
    this.id = id;
    find(id);
  }

  public Track(String title, byte[] audio, Genre genre) {
    this.id = create(title, audio, genre);
    this.title = title;
    this.audio = audio;
    this.genre = genre;
  }

  private int create(String title, byte[] audio, Genre genre) {
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
    return id;
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

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public byte[] getAudio() {
    return audio;
  }

  public Genre getGenre() {
    return genre;
  }
}
