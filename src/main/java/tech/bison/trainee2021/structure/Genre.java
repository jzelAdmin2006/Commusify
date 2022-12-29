package tech.bison.trainee2021.structure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import tech.bison.trainee2021.Commusify;
import tech.bison.trainee2021.userInterface.command.IdChecker;
import tech.bison.trainee2021.userInterface.command.search.Searchable;
import tech.bison.trainee2021.userInterface.command.search.Searcher;

public class Genre implements Searchable {
  @Override
  public int hashCode() {
    return Objects.hash(designation, id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Genre other = (Genre) obj;
    return Objects.equals(designation, other.designation) && id == other.id;
  }

  private final int id;
  private String designation;

  public Genre(int id) {
    this.id = id;
    find(id);
  }

  public Genre(String designation) {
    this.designation = designation;
    id = create(designation);
  }

  private int create(String designation) {
    int id = 0;
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall("{call SP_CREATE_GENRE(?)}");
      callableStatement.setString("Designation", designation);
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
      CallableStatement callableStatement = connection.prepareCall("{call SP_FIND_GENRE(?)}");
      callableStatement.setInt("ID", id);
      ResultSet result = callableStatement.executeQuery();

      result.next();
      designation = result.getString("Designation");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public String getDesignation() {
    return designation;
  }

  public int getId() {
    return id;
  }

  @Override
  public String result() {
    return String.format("Genre: ID = %s, Designation = \"%s\"", id, designation);
  }

  public static class GenreSearcher extends Searcher {

    @Override
    protected String getSearchCallSP() {
      return "SP_SEARCH_GENRE";
    }

    @Override
    public Searchable of(int id) {
      return new Genre(id);
    }
  }

  public static class GenreIdChecker extends IdChecker {
    @Override
    protected String getIdExistsCallSP() {
      return "SP_GENRE_ID_EXISTS";
    }
  }
}
