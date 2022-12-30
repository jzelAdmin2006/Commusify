package tech.bison.trainee2021.framework.structure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import tech.bison.trainee2021.Commusify;
import tech.bison.trainee2021.userInterface.command.IdChecker;
import tech.bison.trainee2021.userInterface.command.search.Searchable;
import tech.bison.trainee2021.userInterface.command.search.Searcher;

public class Artist implements Searchable {

  private final int id;
  private final List<User> members = new ArrayList<>();
  private String name;

  /**
   * This constructor creates a new artist and writes it into the Commusify database
   * 
   * @param members
   *          Member users of the new artist
   * @param name
   *          Name of the new artist
   */
  public Artist(List<User> members, String name) {
    id = create(name, members);
    this.name = name;
  }

  private void addMembers(int id, List<User> members) {
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
    members.add(member);
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

  /**
   * This constructor reads the existing artist with the given ID from the Commusify
   * database
   * 
   * @param id
   *          The ID of the existing artist
   */
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

  /**
   * @return Members of the artist
   */
  public List<User> getMembers() {
    return Collections.unmodifiableList(members);
  }

  /**
   * @return Name of the artist
   */
  public String getName() {
    return name;
  }

  /**
   * @return ID of the artist
   */
  public int getId() {
    return id;
  }

  public static class ArtistIdChecker extends IdChecker {
    @Override
    protected String getIdExistsCallSP() {
      return "SP_ARTIST_ID_EXISTS";
    }
  }

  public static class ArtistSearcher extends Searcher {

    @Override
    protected String getSearchCallSP() {
      return "SP_SEARCH_ARTIST";
    }

    @Override
    public Searchable of(int id) {
      return new Artist(id);
    }
  }

  @Override
  public String result() {
    return String.format("ID: %s, Name: %s, Members: {%s}",
        id,
        name,
        members.stream()
            .map(member -> member.getId())
            .collect(Collectors.toList())
            .stream()
            .map(String::valueOf)
            .collect(Collectors.joining(", ")));
  }
}
