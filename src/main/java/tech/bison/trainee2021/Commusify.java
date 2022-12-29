package tech.bison.trainee2021;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import tech.bison.trainee2021.userInterface.UserInterface;

public class Commusify {
  public static final String DATABASE = "jdbc:sqlserver://SQL5063.site4now.net:1433;username=db_a91f81_commusify_admin;password=825ACAAA44DDD24143C3";

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
