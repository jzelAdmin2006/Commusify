package tech.bison.trainee2021;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class UserTest {
  @Test
  void newUser_getUserName_isCorrect() {
    User user = new User("jzelAdmin2006", "TopsecretPassword12345", "Jzel", "Admin", "jzel2006@admin.ch");

    String result = user.getUserName();

    assertThat(result).isEqualTo("jzelAdmin2006");
  }

  @Test
  void newUserWithDiffrerentUserName_getUserName_isCorrect() {
    User user = new User("jzelAdmin2006_2", "TopsecretPassword12345", "Jzel", "Admin", "jzel2006@admin.ch");

    String result = user.getUserName();

    assertThat(result).isEqualTo("jzelAdmin2006_2");
  }

  @Test
  void newUser_getPasswordHash_isCorrect() {
    User user = new User("jzelAdmin2006", "TopsecretPassword12345", "Jzel", "Admin", "jzel2006@admin.ch");

    int result = user.getPasswordHash();

    assertThat(result).isEqualTo(-1741527469);
  }

  @Test
  void newUserWithDifferentPassword_getPasswordHash_isCorrect() {
    User user = new User("jzelAdmin2006", "TopsecretPassword123456", "Jzel", "Admin", "jzel2006@admin.ch");

    int result = user.getPasswordHash();

    assertThat(result).isEqualTo(1847223363);
  }

  @Test
  void newUser_getFirstName_isCorrect() {
    User user = new User("jzelAdmin2006", "TopsecretPassword123456", "Jzel", "Admin", "jzel2006@admin.ch");

    String result = user.getFirstName();

    assertThat(result).isEqualTo("Jzel");
  }

  @Test
  void newUserWithDifferentFirstName_getFirstName_isCorrect() {
    User user = new User("jzelAdmin2006", "TopsecretPassword123456", "Jzel2", "Admin", "jzel2006@admin.ch");

    String result = user.getFirstName();

    assertThat(result).isEqualTo("Jzel2");
  }

  @Test
  void newUser_getLastName_isCorrect() {
    User user = new User("jzelAdmin2006", "TopsecretPassword123456", "Jzel", "Admin", "jzel2006@admin.ch");

    String result = user.getLastName();

    assertThat(result).isEqualTo("Admin");
  }

  @Test
  void newUserWithDifferentLastName_getLastName_isCorrect() {
    User user = new User("jzelAdmin2006", "TopsecretPassword123456", "Jzel2", "Admin2", "jzel2006@admin.ch");

    String result = user.getLastName();

    assertThat(result).isEqualTo("Admin2");
  }

  @Test
  void newUser_getEmail_isCorrect() {
    User user = new User("jzelAdmin2006", "TopsecretPassword123456", "Jzel", "Admin", "jzel2006@admin.ch");

    String result = user.getEmail();

    assertThat(result).isEqualTo("jzel2006@admin.ch");
  }

  @Test
  void newUserWithDifferentEmail_getEmail_isCorrect() {
    User user = new User("jzelAdmin2006", "TopsecretPassword123456", "Jzel2", "Admin2", "jzel2006@admin.ch2");

    String result = user.getEmail();

    assertThat(result).isEqualTo("jzel2006@admin.ch2");
  }
}
