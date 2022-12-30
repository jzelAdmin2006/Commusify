package tech.bison.trainee2021.structure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import tech.bison.trainee2021.Commusify;
import tech.bison.trainee2021.userInterface.command.search.Searchable;
import tech.bison.trainee2021.userInterface.command.search.Searcher;

public class User implements Searchable {

  private final int id;
  private String userName;
  private int passwordHash;
  private String firstName;
  private String lastName;
  private String email;
  private boolean isLoggedIn;

  /**
   * This constructor creates a new user and writes it into the Commusify database
   * 
   * @param userName
   *          Unique username of the new user
   * @param password
   *          Password of the new user, this will be stored as a hashcode
   * @param firstName
   *          First name of the new user
   * @param lastName
   *          Last name of the new user
   * @param email
   *          Email of the new user, it doesn't have to be unique since users are identified with
   *          the username or the ID, it also doesn't have to be online but the formatting has to be
   *          correct
   */
  public User(String userName, String password, String firstName, String lastName, String email) {
    passwordHash = password.hashCode();
    id = create(userName, passwordHash, firstName, lastName, email);
    this.userName = userName;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  /**
   * This constructor reads the existing user with the given ID from the Commusify
   * database
   * 
   * @param id
   *          The ID of the existing user
   */
  public User(int id) {
    this.id = id;
    find(id);
  }

  /**
   * This constructor reads the existing user with the given username from the Commusify
   * database
   * 
   * @param id
   *          The username of the existing user
   */
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

  /**
   * @return Username of the user
   */
  public String getUserName() {
    return userName;
  }

  /**
   * @return Passwordhashcode of the user
   */
  public int getPasswordHash() {
    return passwordHash;
  }

  /**
   * @return First name of the user
   */
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

  /**
   * @return Last name of the user
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @return Email of the user
   */
  public String getEmail() {
    return email;
  }

  /**
   * @return ID of the user
   */
  public int getId() {
    return id;
  }

  /**
   * This can be used to login with a user
   * 
   * @param password
   *          The password that was entered
   * @return True = the login was successful resp. the password was correct
   *         False = the login wasn't successful resp. the password was wrong
   */
  public boolean login(String password) {
    if (password.hashCode() == passwordHash) {
      isLoggedIn = true;
      return true;
    } else {
      return false;
    }
  }

  /**
   * @return True = the user is logged in
   *         False = the user isn't logged in
   */
  public boolean isLoggedIn() {
    return isLoggedIn;
  }

  /**
   * @param userName
   *          The username that you want to check if it exists
   * @return True = the username exists in the Commusify database
   *         False = the username doesn't exist in the Commusify database
   */
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

  /**
   * @param artist
   *          The artist you want to know if this user is a member from or not
   * @return True = This user is a member of the artist, he thus should be allowed to do things like
   *         creating tracks with the artist as an interpreter.
   */
  public boolean isArtistMember(Artist artist) {
    for (User member : artist.getMembers()) {
      if (this.equals(member)) {
        return true;
      }
    }
    return false;
  }

  public static class UserSearcher extends Searcher {

    @Override
    protected String getSearchCallSP() {
      return "SP_SEARCH_USER";
    }

    @Override
    public Searchable of(int id) {
      return new User(id);
    }
  }

  @Override
  public String result() {
    return String.format("ID = %s, Username = \"%s\", first name = \"%s\", last name = \"%s\", E-Mail = \"%s\"",
        id,
        userName,
        firstName,
        lastName,
        email);
  }
}
