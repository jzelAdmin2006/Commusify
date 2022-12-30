package tech.bison.trainee2021.framework.playable.specialTrack;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tech.bison.trainee2021.Commusify;
import tech.bison.trainee2021.framework.playable.Track;
import tech.bison.trainee2021.framework.structure.Artist;
import tech.bison.trainee2021.framework.structure.Genre;

public class Mashup extends Track {

  private final List<Track> originalTracks = new ArrayList<>();

  /**
   * This constructor creates a new mashup and writes it into the Commusify database
   * 
   * @param title
   *          The title of the new mashup
   * @param audio
   *          The audio data of the new mashup
   * @param genre
   *          The genre of the new mashup
   * @param interpreters
   *          All interpreters of the new mashup
   * @param originalTracks
   *          All original tracks of the new mashup
   */
  public Mashup(String title, byte[] audio, Genre genre, List<Artist> interpreters, List<Track> originalTracks) {
    super(title, audio, genre, interpreters);
    addOriginalTracks(originalTracks, super.getId());
  }

  /**
   * This constructor reads the existing mashup with the given ID from the Commusify
   * database
   * 
   * @param id
   *          The ID of the existing mashup
   */
  public Mashup(int id) {
    super(id);
    find(id);
  }

  private void addOriginalTracks(List<Track> originalTracks, int id) {
    for (Track originalTrack : originalTracks) {
      addOriginalTrack(originalTrack, id);
    }
  }

  private void addOriginalTrack(Track originalTrack, int id) {
    originalTracks.add(originalTrack);
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall("{call SP_ADD_TRACK_EDIT(?, ?)}");
      callableStatement.setInt("EditedTrackID", id);
      callableStatement.setInt("OriginalTrackID", originalTrack.getId());
      callableStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void find(int id) {
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall("{call SP_FIND_TRACK_EDIT(?)}");
      callableStatement.setInt("ID", id);
      ResultSet result = callableStatement.executeQuery();

      while (result.next()) {
        originalTracks.add(new Track(result.getInt("FK_OriginalTrackID")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * @return All original tracks of the mashup
   */
  public List<Track> getOriginalTracks() {
    return Collections.unmodifiableList(originalTracks);
  }
}
