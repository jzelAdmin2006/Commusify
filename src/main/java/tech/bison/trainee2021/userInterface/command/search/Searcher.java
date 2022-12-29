package tech.bison.trainee2021.userInterface.command.search;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tech.bison.trainee2021.Commusify;

public abstract class Searcher {
  public List<Searchable> search(String search) {
    List<Searchable> results = new ArrayList<>();
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall(String.format("{call %s(?)}", getSearchCallSP()));
      callableStatement.setString("Search", search);
      ResultSet result = callableStatement.executeQuery();

      while (result.next()) {
        int id = result.getInt("ID");
        if (id != Commusify.INVALID_ID) {
          results.add(of(id));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return results;
  }

  protected abstract String getSearchCallSP();

  public abstract Searchable of(int id);
}
