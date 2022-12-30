package tech.bison.trainee2021.framework.playable.specificPlayableList;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import tech.bison.trainee2021.Commusify;
import tech.bison.trainee2021.framework.playable.Track;
import tech.bison.trainee2021.framework.playable.specificPlayableList.albumType.MixTape;
import tech.bison.trainee2021.framework.playable.specificPlayableList.albumType.recordType.DoubleLongPlay;
import tech.bison.trainee2021.framework.playable.specificPlayableList.albumType.recordType.ExtendedPlay;
import tech.bison.trainee2021.framework.playable.specificPlayableList.albumType.recordType.LongPlay;
import tech.bison.trainee2021.framework.playable.specificPlayableList.albumType.recordType.Single;
import tech.bison.trainee2021.framework.structure.Artist;
import tech.bison.trainee2021.userInterface.command.search.Searchable;

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

    /**
     * @param type
     *          The known album type you want to know the code from
     * @return The code of the known album type with which the type information is stored in the
     *         Commusify database
     */
    private static int code(AlbumType type) {
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

    /**
     * @param id
     *          The ID of the album you want
     * @param typeCode
     *          The typecode of the known
     * @return The album with the given ID with the correct type
     */
    public static Album of(int id, int typeCode) {
      for (AlbumType albumType : AlbumType.values()) {
        if (typeCode == AlbumType.code(albumType)) {
          switch (albumType) {
            case DOUBLE_LONG_PLAY:
              return new DoubleLongPlay(id);
            case EXTENDED_PLAY:
              return new ExtendedPlay(id);
            case LONG_PLAY:
              return new LongPlay(id);
            case MIX_TAPE:
              return new MixTape(id);
            case SINGLE:
              return new Single(id);
          }
        }
      }
      throw new IllegalArgumentException(String.format("The type for the album with the id %s couldn't be found.", id));
    }
  }

  private final List<Artist> interpreters = new ArrayList<>();

  /**
   * @return The type of the album
   */
  protected abstract AlbumType type();

  /**
   * This constructor reads the existing album with the given ID from the Commusify
   * database
   * 
   * @param id
   *          The ID of the existing album
   */
  protected Album(int id) {
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

  /**
   * This constructor creates a new album and writes it into the Commusify database
   * 
   * @param title
   *          Title of the new album
   * @param tracks
   *          All tracks of the new album
   * @param interpreters
   *          All interpreters of the new album
   */
  public Album(String title, List<Track> tracks, List<Artist> interpreters) {
    super(title, tracks);
    create(interpreters);
  }

  private void create(List<Artist> interpreters) {
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall("{call SP_CREATE_ALBUM(?, ?)}");
      callableStatement.setInt("Type", AlbumType.code(type()));
      callableStatement.setInt("PlayableListID", super.getId());
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

  /**
   * @return Interpreters of the album
   */
  public List<Artist> getInterpreters() {
    return Collections.unmodifiableList(interpreters);
  }

  /**
   * @param id
   *          The ID of the album you want
   * @return The album with the given ID with the correct type
   */
  public static Album of(int id) {
    int typeCode = 0;
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall("{call SP_FIND_ALBUM_TYPE(?)}");
      callableStatement.setInt("ID", id);
      ResultSet result = callableStatement.executeQuery();

      if (result.next()) {
        typeCode = result.getInt("Type");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return AlbumType.of(id, typeCode);
  }

  @Override
  public String result() {
    return String.format("Album with type %s, albuminterpreter(s): {%s} (%s)",
        type(),
        interpreters.stream()
            .map(interpreter -> interpreter.getId())
            .collect(Collectors.toList())
            .stream()
            .map(String::valueOf)
            .collect(Collectors.joining(", ")),
        super.result());
  }

  public static class AlbumSearcher extends PlayableListSearcher {
    @Override
    public String getSearchCallSP() {
      return "SP_SEARCH_ALBUM";
    }

    @Override
    public Searchable of(int id) {
      return Album.of(id);
    }
  }

  public static class AlbumIdChecker extends PlayableListIdChecker {
    @Override
    protected String getIdExistsCallSP() {
      return "SP_ALBUM_ID_EXISTS";
    }
  }
}
