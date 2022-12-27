package tech.bison.trainee2021.playable;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import tech.bison.trainee2021.Commusify;

public class Playlist {

  @Override
  public int hashCode() {
    return Objects.hash(id, title, tracks);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Playlist other = (Playlist) obj;
    return id == other.id && Objects.equals(title, other.title) && Objects.equals(tracks, other.tracks);
  }

  private String title;
  private final List<Track> tracks = new ArrayList<>();
  private final int id;

  public Playlist(String title, List<Track> tracks) {
    this.id = create(title, tracks);
    this.title = title;
  }

  private int create(String title, List<Track> tracks) {
    int id = 0;
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall("{call SP_CREATE_PLAYLIST(?)}");
      callableStatement.setString("Title", title);
      ResultSet result = callableStatement.executeQuery();

      result.next();
      id = result.getInt("ID");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    addTracks(id, tracks);
    return id;
  }

  protected void addTracks(int id, List<Track> tracks) {
    for (Track track : tracks) {
      addTrack(id, track);
    }
  }

  private void addTrack(int id, Track track) {
    tracks.add(track);
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall("{call SP_ADD_PLAYLIST_TRACK(?, ?)}");
      callableStatement.setInt("PlaylistID", id);
      callableStatement.setInt("TrackID", track.getId());
      callableStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Playlist(int id) {
    this.id = id;
    find(id);
  }

  private void find(int id) {
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall("{call SP_FIND_PLAYLIST(?)}");
      callableStatement.setInt("ID", id);
      ResultSet result = callableStatement.executeQuery();

      result.next();
      title = result.getString("Title");
      do {
        tracks.add(new Track(result.getInt("FK_TrackID")));
      } while (result.next());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public String getTitle() {
    return title;
  }

  public List<Track> getTracks() {
    return Collections.unmodifiableList(tracks);
  }

  public int getId() {
    return id;
  }

}
