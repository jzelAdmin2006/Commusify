package tech.bison.trainee2021.structure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import tech.bison.trainee2021.Commusify;

public class SubGenre extends Genre {
  private Genre superGenre;

  /**
   * This constructor reads the existing subgenre with the given ID from the Commusify
   * database
   * 
   * @param id
   *          The ID of the existing subgenre
   */
  public SubGenre(int id) {
    super(id);
    find();
  }

  private void find() {
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall("{call SP_FIND_SUB_GENRE(?)}");
      callableStatement.setInt("ID", getId());
      ResultSet result = callableStatement.executeQuery();

      result.next();
      superGenre = new Genre(result.getInt("FK_SuperGenreID"));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * This constructor creates a new single album and writes it into the Commusify database
   * 
   * @param designation
   *          Designation of the new subgnre
   * @param superGenre
   *          Genre of which this new genre should be a subgenre
   */
  public SubGenre(String designation, Genre superGenre) {
    super(designation);
    this.superGenre = superGenre;
    create(superGenre);
  }

  private void create(Genre superGenre) {
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall("{call SP_CREATE_SUB_GENRE(?, ?)}");
      callableStatement.setInt("SubGenreID", getId());
      callableStatement.setInt("SuperGenreID", superGenre.getId());
      callableStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * @return The genre of which this is a subgenre
   */
  public Genre getSuperGenre() {
    return superGenre;
  }
}
