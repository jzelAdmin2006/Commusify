package tech.bison.trainee2021.framework.structure;

import java.util.UUID;

public class GuestUser extends User {

  private static final String NO_USERNAME = "";
  private static String userName = NO_USERNAME;

  /**
   * Creates a new user for Commusify sessions without a login
   */
  public GuestUser() {
    super(generateUserName(), UUID.randomUUID().toString(), generateUserName(), generateUserName(),
        generateUserName() + "@commusify.com");
  }

  private static String generateUserName() {
    if (userName.equals(NO_USERNAME)) {
      return "GuestUser" + UUID.randomUUID();
    } else {
      return userName;
    }
  }

  @Override
  public boolean isLoggedIn() {
    return false;
  }
}
