package tech.bison.trainee2021;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Commusify {
  public static final String DATABASE = "jdbc:sqlserver://localhost:1433;databaseName=Commusify;username=JDBCAdmin;password=12345;encrypt=true;trustServerCertificate=true;";

  public static void main(String[] args) throws IOException {

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
