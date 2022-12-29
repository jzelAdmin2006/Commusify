package tech.bison.trainee2021.userInterface.command;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import tech.bison.trainee2021.Commusify;

public abstract class IdChecker {
  public boolean exists(int id) {
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall(String.format("{call %s(?)}", getIdExistsCallSP()));
      callableStatement.setInt("ID", id);
      ResultSet result = callableStatement.executeQuery();

      result.next();
      return result.getBoolean("ID_EXISTS");
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  protected abstract String getIdExistsCallSP();
}
