package tech.bison.trainee2021.playable.specialPlaylist;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tech.bison.trainee2021.Commusify;
import tech.bison.trainee2021.playable.Playlist;
import tech.bison.trainee2021.playable.Track;
import tech.bison.trainee2021.structure.Artist;

public abstract class Album extends Playlist {

  protected enum AlbumType {
    MIX_TAPE,
    EXTENDED_PLAY,
    SINGLE,
    LONG_PLAY,
    DOUBLE_LONG_PLAY;

    private static final int MIX_TAPE_CODE = 1;
    private static final int EXTENDED_PLAY_CODE = 2;
    private static final int SINGLE_CODE = 3;
    private static final int LONG_PLAY_CODE = 4;
    private static final int DOUBLE_LONG_PLAY_CODE = 5;

    public static int code(AlbumType type) {
      switch (type) {
        case MIX_TAPE:
          return MIX_TAPE_CODE;
        case EXTENDED_PLAY:
          return EXTENDED_PLAY_CODE;
        case SINGLE:
          return SINGLE_CODE;
        case LONG_PLAY:
          return LONG_PLAY_CODE;
        case DOUBLE_LONG_PLAY:
          return DOUBLE_LONG_PLAY_CODE;
      }
      // should never happen
      throw new UnsupportedOperationException(String.format("Type %s isn't implemented.", type));
    }
  }

  private final List<Artist> interpreters = new ArrayList<>();

  protected abstract AlbumType type();

  public Album(int id) {
    super(id);
    find(id);
  }

  private void find(int id) {
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall("{call SP_FIND_ALBUM(?)}");
      callableStatement.setInt("ID", id);
      ResultSet result = callableStatement.executeQuery();

      while (result.next()) {
        interpreters.add(new Artist(result.getInt("FK_ArtistID")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Album(String title, List<Track> tracks, List<Artist> interpreters) {
    super(title, tracks);
    create(interpreters);
  }

  private void create(List<Artist> interpreters) {
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall("{call SP_CREATE_ALBUM(?, ?)}");
      callableStatement.setInt("Type", AlbumType.code(type()));
      callableStatement.setInt("PlaylistID", super.getId());
      callableStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    addInterpreters(super.getId(), interpreters);
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
      CallableStatement callableStatement = connection.prepareCall("{call SP_ADD_ALBUM_INTERPRETER(?, ?)}");
      callableStatement.setInt("AlbumID", id);
      callableStatement.setInt("InterpreterArtistID", interpreter.getId());
      callableStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public List<Artist> getInterpreters() {
    return Collections.unmodifiableList(interpreters);
  }
}
