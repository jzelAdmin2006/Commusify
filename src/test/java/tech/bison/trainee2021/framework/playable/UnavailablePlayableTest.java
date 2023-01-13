package tech.bison.trainee2021.framework.playable;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class UnavailablePlayableTest {

  @Test
  void newUnavailablePlayable_isAvailable_isFalse() {
    Playable playable = new UnavailablePlayable();

    boolean result = playable.isAvailable();

    assertThat(result).isFalse();
  }
}
