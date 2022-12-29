package tech.bison.trainee2021.structure;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class SubGenreTest {
  @Test
  void newSubGenreWithSuperGenre_getSuperGenre_isTheSame() {
    Genre superGenre = new Genre("Super Genre");
    SubGenre subGenre = new SubGenre("Sub Genre", superGenre);

    Genre result = subGenre.getSuperGenre();

    assertThat(result).isEqualTo(superGenre);
  }

  @Test
  void newSubGenreWithDifferentSuperGenre_getSuperGenre_isTheSame() {
    Genre superGenre = new Genre("Super Genre 2.0");
    SubGenre subGenre = new SubGenre("Sub Genre", superGenre);

    Genre result = subGenre.getSuperGenre();

    assertThat(result).isEqualTo(superGenre);
  }

  @Test
  void newSubGenre_getSuperGenreFromGenreWithSameID_isTheSame() {
    Genre superGenre = new Genre("Super Genre");
    SubGenre subGenre = new SubGenre("Sub Genre", superGenre);

    Genre result = new SubGenre(subGenre.getId()).getSuperGenre();

    assertThat(result).isEqualTo(superGenre);
  }

  @Test
  void newDifferentSubGenre_getSuperGenreFromGenreWithSameID_isTheSame() {
    Genre superGenre = new Genre("Super Genre 2.0");
    SubGenre subGenre = new SubGenre("Sub Genre 2.0", superGenre);

    Genre result = new SubGenre(subGenre.getId()).getSuperGenre();

    assertThat(result).isEqualTo(superGenre);
  }
}
