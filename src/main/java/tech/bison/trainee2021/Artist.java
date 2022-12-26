package tech.bison.trainee2021;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Artist {

  private final int id;
  private List<User> members = new ArrayList<>();
  private String name;

  public Artist(List<User> members, String name) {
    id = create(name, members);
    this.name = name;
  }

  private void addMembers(int id, List<User> members) {
    this.members.addAll(members);
    for (User member : members) {
      addMember(id, member);
    }
  }

  private int create(String name, List<User> members) {
    int id = 0;
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall("{call SP_CREATE_ARTIST(?)}");
      callableStatement.setString("Name", name);
      ResultSet result = callableStatement.executeQuery();

      result.next();
      id = result.getInt("ID");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    addMembers(id, members);
    return id;
  }

  private void addMember(int id, User member) {
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall("{call SP_ADD_ARTIST_MEMBER(?, ?)}");
      callableStatement.setInt("ArtistID", id);
      callableStatement.setInt("MemberUserID", member.getId());
      callableStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Artist(int id) {
    this.id = id;
    find(id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, members, name);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Artist other = (Artist) obj;
    return id == other.id && Objects.equals(members, other.members) && Objects.equals(name, other.name);
  }

  private void find(int id) {
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall("{call SP_FIND_ARTIST(?)}");
      callableStatement.setInt("ID", id);
      ResultSet result = callableStatement.executeQuery();

      result.next();
      name = result.getString("Name");
      do {
        members.add(new User(result.getInt("FK_UserID")));
      } while (result.next());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public List<User> getMembers() {
    return members;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }
}
