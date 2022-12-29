package tech.bison.trainee2021.userInterface.command;

public abstract class IdChecker {
  public boolean exists(int id) {
    return false;
    // List<Searchable> results = new ArrayList<>();
    // try {
    // Connection connection = DriverManager.getConnection(Commusify.DATABASE);
    // CallableStatement callableStatement = connection.prepareCall(String.format("{call %s(?)}",
    // getSearchCallSP()));
    // callableStatement.setString("Search", search);
    // ResultSet result = callableStatement.executeQuery();
    //
    // while (result.next()) {
    // int id = result.getInt("ID");
    // if (id != Commusify.INVALID_ID) {
    // results.add(of(id));
    // }
    // }
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // return results;
  }

  protected abstract String getIdExistsCallSP();
}
