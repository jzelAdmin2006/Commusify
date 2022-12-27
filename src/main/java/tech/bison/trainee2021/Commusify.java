package tech.bison.trainee2021;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import tech.bison.trainee2021.userInterface.UserInterface;

public class Commusify {
  public static final String DATABASE = "jdbc:sqlserver://localhost:1433;databaseName=Commusify;username=JDBCAdmin;password=12345;encrypt=true;trustServerCertificate=true;";

  public static final int INVALID_ID = 0;

  public static void main(String[] args) throws IOException {
    UserInterface userInterface = new UserInterface();
    userInterface.run();
  }

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
