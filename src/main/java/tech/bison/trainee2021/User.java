package tech.bison.trainee2021;

public class User {

  private final int id;
  private int passwordHash;
  private String userName;
  private String firstName;
  private String lastName;
  private String email;

  public User(String userName, String password, String firstName, String lastName, String email) {
    this.passwordHash = password.hashCode();
    id = create(userName, firstName, lastName, email);
    this.userName = userName;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  private int create(String userName, String firstName, String lastName, String email) {
    return 0;
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

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public int getId() {
    return id;
  }
}
