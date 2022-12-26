package tech.bison.trainee2021.structure;

import java.util.List;

import tech.bison.trainee2021.playable.Track;

public class Interpretation {

  // TODO implement this after album
  private Track track;
  private List<Artist> artists;
  private int id;

  public Interpretation(List<Artist> artists, Track track) {
    // id = create(artists, track);
    this.track = track;
    this.artists = artists;
  }

  // private int create(List<Artist> artists, Track track) {
  // int id = 0;
  // try {
  // Connection connection = DriverManager.getConnection(Commusify.DATABASE);
  // CallableStatement callableStatement = connection.prepareCall("{call SP_CREATE_INTERPRETATION(?,
  // ?)}");
  // callableStatement.setA("ArtistID", arti);
  // callableStatement.setInt("GenreID", genre.getId());
  // ResultSet result = callableStatement.executeQuery();
  //
  // result.next();
  // id = result.getInt("ID");
  // } catch (SQLException e) {
  // e.printStackTrace();
  // }
  // return id;
  // }

  public Interpretation(int id) {
    this.id = id;
    find(id);
  }

  private void find(int id) {
    // TODO Auto-generated method stub

  }

  public Track getTrack() {
    return track;
  }

  public List<Artist> getArtists() {
    return artists;
  }

  public int getId() {
    return 0;
  }
}
