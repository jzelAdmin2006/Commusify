package tech.bison.trainee2021;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import tech.bison.trainee2021.userInterface.UserInterface;

/**
 * @author johannes.zeller: I built the music service Commusify as a school project.
 */
public class Commusify {
  /**
   * This is the JDBC URL for the Commusify database. This is an online database on the main branch,
   * otherwise you can also use local SQL servers here, but of course they must be the same.
   */
  public static final String DATABASE = "jdbc:sqlserver://SQL5063.site4now.net:1433;username=db_a91f81_commusify_admin;password=825ACAAA44DDD24143C3";

  /**
   * This can be used to create invalid Commusify objects. This ID will always stand for something
   * invalid.
   */
  public static final int INVALID_ID = 0;

  /**
   * Starts Commusify
   * 
   * @param args:
   *          This will be ignored, arguments aren't necessary to start Commusify
   */
  public static void main(String[] args) {
    UserInterface userInterface = new UserInterface();
    userInterface.run();
  }

  /**
   * Resets the whole Commusify database. This can be used for testing, so you don't always have to
   * pay attention to unique user names, for example.
   */
  public static void reset() {
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      Statement statement = connection.createStatement();
      statement.execute("SP_RESETALL");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
