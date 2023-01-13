package tech.bison.trainee2021.framework.playable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class UnavailablePlayableTest {

  @Test
  void newUnavailablePlayable_isAvailable_isFalse() {
    Playable playable = new UnavailablePlayable();

    boolean result = playable.isAvailable();

    assertThat(result).isFalse();
  }

  @Test
  void newUnavailablePlayable_interactWithPlayable_isNotAlloweds() {
    Playable playable = new UnavailablePlayable();

    assertThatThrownBy(() -> playable.result()).isEqualTo(UnavailablePlayable.PLAYABLE_IS_NOT_AVAILABLE);
    assertThatThrownBy(() -> playable.getId()).isEqualTo(UnavailablePlayable.PLAYABLE_IS_NOT_AVAILABLE);
    assertThatThrownBy(() -> playable.play()).isEqualTo(UnavailablePlayable.PLAYABLE_IS_NOT_AVAILABLE);
    assertThatThrownBy(() -> playable.playNext()).isEqualTo(UnavailablePlayable.PLAYABLE_IS_NOT_AVAILABLE);
    assertThatThrownBy(() -> playable.playPrevious()).isEqualTo(UnavailablePlayable.PLAYABLE_IS_NOT_AVAILABLE);
    assertThatThrownBy(() -> playable.shuffle(false)).isEqualTo(UnavailablePlayable.PLAYABLE_IS_NOT_AVAILABLE);
    assertThatThrownBy(() -> playable.shuffle(true)).isEqualTo(UnavailablePlayable.PLAYABLE_IS_NOT_AVAILABLE);
    assertThatThrownBy(() -> playable.loop(false)).isEqualTo(UnavailablePlayable.PLAYABLE_IS_NOT_AVAILABLE);
    assertThatThrownBy(() -> playable.loop(true)).isEqualTo(UnavailablePlayable.PLAYABLE_IS_NOT_AVAILABLE);
    assertThatThrownBy(() -> playable.download()).isEqualTo(UnavailablePlayable.PLAYABLE_IS_NOT_AVAILABLE);
  }
}
