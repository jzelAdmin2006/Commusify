package tech.bison.trainee2021;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Commusify {
  public static final String DATABASE = "jdbc:sqlserver://SQL5063.site4now.net:1433;username=db_a91f81_commusify_admin;password=825ACAAA44DDD24143C3";

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
