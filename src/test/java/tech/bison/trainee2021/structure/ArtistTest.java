package tech.bison.trainee2021;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArtistTest {
  @BeforeEach
  void resetDatabase() {
    Commusify.reset();
  }

  @Test
  void newArtistWithMember_getMembers_isTheSame() {
    User member = new User("jzelAdmin2006", "TopsecretPassword123456", "Jzel2", "Admin", "jzel2006@admin.ch");
    List<User> members = new ArrayList<>();
    members.add(member);
    Artist artist = new Artist(members, "jzelAdmin2006Artist");

    List<User> result = artist.getMembers();

    assertThat(result).containsExactly(member);
  }

  @Test
  void newArtistWithMembers_getMembers_isTheSame() {
    User member1 = new User("jzelAdmin2006", "TopsecretPassword123456", "Jzel2", "Admin", "jzel2006@admin.ch");
    User member2 = new User("jzelAdmin2006_2", "TopsecretPassword123456", "Jzel2", "Admin", "jzel2006@admin.ch");
    List<User> members = new ArrayList<>();
    members.add(member1);
    members.add(member2);
    Artist artist = new Artist(members, "jzelAdmin2006Artist and jzelAdmin2006_2Artist");

    List<User> result = artist.getMembers();

    assertThat(result).containsExactly(member1, member2);
  }

  @Test
  void newArtistWithName_getName_isTheSame() {
    User member = new User("jzelAdmin2006", "TopsecretPassword123456", "Jzel2", "Admin", "jzel2006@admin.ch");
    List<User> members = new ArrayList<>();
    members.add(member);
    Artist artist = new Artist(members, "jzelAdmin2006Artist");

    String result = artist.getName();

    assertThat(result).isEqualTo("jzelAdmin2006Artist");
  }

  @Test
  void newArtistWithDifferentName_getName_isTheSame() {
    User member = new User("jzelAdmin2006", "TopsecretPassword123456", "Jzel2", "Admin", "jzel2006@admin.ch");
    List<User> members = new ArrayList<>();
    members.add(member);
    Artist artist = new Artist(members, "jzelAdmin2006Artist2");

    String result = artist.getName();

    assertThat(result).isEqualTo("jzelAdmin2006Artist2");
  }

  @Test
  void newArtist_artistWithSameId_isEqual() {
    User member = new User("jzelAdmin2006", "TopsecretPassword123456", "Jzel2", "Admin", "jzel2006@admin.ch");
    List<User> members = new ArrayList<>();
    members.add(member);
    Artist artist = new Artist(members, "jzelAdmin2006Artist");
    Artist artistWithSameId = new Artist(artist.getId());

    assertThat(artist).isEqualTo(artistWithSameId);
    assertThat(artist.getMembers()).containsExactlyElementsOf(artistWithSameId.getMembers()).containsExactly(member);
    assertThat(artist.getName()).isEqualTo(artistWithSameId.getName()).isEqualTo("jzelAdmin2006Artist");
  }

  @Test
  void newDifferentArtist_artistWithSameId_isEqual() {
    User member1 = new User("jzelAdmin2006", "TopsecretPassword123456", "Jzel2", "Admin", "jzel2006@admin.ch");
    User member2 = new User("jzelAdmin2006_2", "TopsecretPassword123456", "Jzel2", "Admin", "jzel2006@admin.ch");
    List<User> members = new ArrayList<>();
    members.add(member1);
    members.add(member2);
    Artist artist = new Artist(members, "jzelAdmin2006Artist");
    Artist artistWithSameId = new Artist(artist.getId());

    assertThat(artist).isEqualTo(artistWithSameId);
    assertThat(artist.getMembers()).containsExactlyElementsOf(artistWithSameId.getMembers())
        .containsExactly(member1, member2);
    assertThat(artist.getName()).isEqualTo(artistWithSameId.getName()).isEqualTo("jzelAdmin2006Artist");
  }
}
