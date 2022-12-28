package tech.bison.trainee2021.structure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import tech.bison.trainee2021.Commusify;

public class User {

  private final int id;
  private String userName;
  private int passwordHash;
  private String firstName;
  private String lastName;
  private String email;
  private boolean isLoggedIn;

  public User(String userName, String password, String firstName, String lastName, String email) {
    passwordHash = password.hashCode();
    id = create(userName, passwordHash, firstName, lastName, email);
    this.userName = userName;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  public User(int id) {
    this.id = id;
    find(id);
  }

  public User(String userName) {
    this.userName = userName;
    id = find(userName);
  }

  private int find(String userName) {
    int id = 0;
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall("{call SP_FIND_USER_BY_USERNAME(?)}");
      callableStatement.setString("UserName", userName);
      ResultSet result = callableStatement.executeQuery();

      result.next();
      id = result.getInt("ID");
      passwordHash = result.getInt("PasswordHash");
      firstName = result.getString("FirstName");
      lastName = result.getString("LastName");
      email = result.getString("Email");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return id;
  }

  private void find(int id) {
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall("{call SP_FIND_USER(?)}");
      callableStatement.setInt(1, id);
      ResultSet result = callableStatement.executeQuery();

      result.next();
      userName = result.getString("UserName");
      passwordHash = result.getInt("PasswordHash");
      firstName = result.getString("FirstName");
      lastName = result.getString("LastName");
      email = result.getString("Email");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private int create(String userName, int passwordHash, String firstName, String lastName, String email) {
    int id = 0;
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall("{call SP_CREATE_USER(?, ?, ?, ?, ?)}");
      callableStatement.setString(1, userName);
      callableStatement.setInt(2, passwordHash);
      callableStatement.setString(3, firstName);
      callableStatement.setString(4, lastName);
      callableStatement.setString(5, email);
      ResultSet result = callableStatement.executeQuery();

      result.next();
      id = result.getInt("ID");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return id;
  }

  public String getUserName() {
    return userName;
  }

  public int getPasswordHash() {
    return passwordHash;
  }

  public String getFirstName() {
    return firstName;
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, firstName, id, lastName, passwordHash, userName);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    User other = (User) obj;
    return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName) && id == other.id
        && Objects.equals(lastName, other.lastName) && passwordHash == other.passwordHash
        && Objects.equals(userName, other.userName);
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public int getId() {
    return id;
  }

  public boolean login(String password) {
    if (password.hashCode() == passwordHash) {
      isLoggedIn = true;
      return true;
    } else {
      return false;
    }
  }

  public boolean isLoggedIn() {
    return isLoggedIn;
  }

  public static boolean userNameExists(String userName) {
    boolean exists = false;
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall("{call SP_FIND_USER_BY_USERNAME(?)}");
      callableStatement.setString("UserName", userName);
      ResultSet result = callableStatement.executeQuery();

      exists = result.next();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return exists;
  }
}
