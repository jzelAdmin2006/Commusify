package tech.bison.trainee2021;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class GenreTest {
  @Test
  void newGenre_getDesignation_isCorrect() {
    Genre genre = new Genre("asdf");

    String result = genre.getDesignation();

    assertThat(result).isEqualTo("asdf");
  }

  @Test
  void newGenreWithDifferentDesignation_getDesignation_isCorrect() {
    Genre genre = new Genre("qwert");

    String result = genre.getDesignation();

    assertThat(result).isEqualTo("qwert");
  }

  @Test
  void newGenre_genreWithSameID_isEqual() {
    Genre genre = new Genre("asdf");
    Genre genreWithSameID = new Genre(genre.getId());

    assertThat(genre).isEqualTo(genreWithSameID);
    assertThat(genre.getId()).isEqualTo(genreWithSameID.getId());
    assertThat(genre.getDesignation()).isEqualTo(genreWithSameID.getDesignation());
  }

  @Test
  void newGenreWithDifferentDesignation_genreWithSameID_isEqual() {
    Genre genre = new Genre("qwert");
    Genre genreWithSameID = new Genre(genre.getId());

    assertThat(genre).isEqualTo(genreWithSameID);
    assertThat(genre.getId()).isEqualTo(genreWithSameID.getId());
    assertThat(genre.getDesignation()).isEqualTo(genreWithSameID.getDesignation());
  }
}
