package tech.bison.trainee2021;

import java.util.List;

public class Artist {

  private List<User> members;

  public Artist(List<User> members) {
    this.members = members;
  }

  public List<User> getMembers() {
    return members;
  }
}
