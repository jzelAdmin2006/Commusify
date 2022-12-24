package tech.bison.trainee2021.playable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import tech.bison.trainee2021.Commusify;
import tech.bison.trainee2021.Genre;

public class Track {
  private final int id;
  private String title;
  private byte[] audio;
  private Genre genre;

  public Track(int id) {
    this.id = id;
    find(id);
  }

  private void find(int id) {
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      Statement statement = connection.createStatement();
      ResultSet result = statement.executeQuery("SP_FIND_TRACK");

      result.next();
      title = result.getString("Title");
      audio = result.getBytes("Audio");
      genre = new Genre(result.getInt("FK_GenreID"));
    } catch (SQLException e) {
      e.printStackTrace();
    }
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
