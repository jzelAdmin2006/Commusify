package tech.bison.trainee2021.playable.specialPlaylist.albumType;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import tech.bison.trainee2021.Commusify;
import tech.bison.trainee2021.playable.Track;
import tech.bison.trainee2021.playable.specialPlaylist.Album;
import tech.bison.trainee2021.structure.Artist;

public class MixTape extends Album {

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + Objects.hash(description);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    MixTape other = (MixTape) obj;
    return Objects.equals(description, other.description);
  }

  private String description;

  public MixTape(String title, List<Track> tracks, List<Artist> interpreters, String description) {
    super(title, tracks, interpreters);
    this.description = description;
    create(description);
  }

  private void create(String description) {
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall("{call SP_CREATE_MIX_TAPE(?, ?)}");
      callableStatement.setInt("ID", super.getId());
      callableStatement.setString("Description", description);
      callableStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public MixTape(int id) {
    super(id);
    find(id);
  }

  private void find(int id) {
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall("{call SP_FIND_MIX_TAPE(?)}");
      callableStatement.setInt("ID", id);
      ResultSet result = callableStatement.executeQuery();

      result.next();
      description = result.getString("Description");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected AlbumType type() {
    return AlbumType.MIX_TAPE;
  }

  public String getDescription() {
    return description;
  }
}
