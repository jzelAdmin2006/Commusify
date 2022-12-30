package tech.bison.trainee2021.playable.specificPlayableList.albumType;

import java.util.List;

import tech.bison.trainee2021.playable.Playable;
import tech.bison.trainee2021.playable.Track;
import tech.bison.trainee2021.playable.specificPlayableList.Album;
import tech.bison.trainee2021.structure.Artist;

public abstract class Record extends Album {
  protected abstract int limit();

  public Record(String title, List<Track> tracks, List<Artist> interpreters) {
    super(title, tracks, interpreters);
  }

  public Record(int id) {
    super(id);
  }

  @Override
  protected void addPlayables(int id, List<Playable> playables) {
    if (playables.size() + getPlayables().size() <= limit()) {
      super.addPlayables(id, playables);
    } else {
      throw new IllegalArgumentException(String
          .format("Records of type %s can't contain more than %s track(s).", getClass().getSimpleName(), limit()));
    }
  }

  public enum KnownRecordType {
    SINGLE,
    EXTENDED_PLAY,
    LONG_PLAY,
    DOUBLE_LONG_PLAY,
    NOT_FOUND;

    private static final String DOUBLE_LONG_PLAY_SPELLING = "DoubleLongPlay";
    private static final String EXTENDED_PLAY_SPELLING = "ExtendedPlay";
    private static final String LONG_PLAY_SPELLING = "LongPlay";
    private static final String SINGLE_SPELLING = "Single";
    private static final String NOT_FOUND_MESSAGE_SPELLING = "If your record type %s is invalid, the message will tell you.";

    public static String getSpellings() {
      boolean isFirstSpelling = true;
      String spellings = "";
      for (KnownRecordType knownRecordType : KnownRecordType.values()) {
        if (isFirstSpelling) {
          isFirstSpelling = false;
        } else {
          spellings += " / ";
        }
        spellings += knownRecordType.spelling();
      }
      return spellings;
    }

    public static KnownRecordType translate(String spelling) {
      for (KnownRecordType knownRecordType : KnownRecordType.values()) {
        if (knownRecordType != NOT_FOUND && spelling.equals(knownRecordType.spelling())) {
          return knownRecordType;
        }
      }
      return NOT_FOUND;
    }

    private String spelling() {
      switch (this) {
        case DOUBLE_LONG_PLAY:
          return DOUBLE_LONG_PLAY_SPELLING;
        case EXTENDED_PLAY:
          return EXTENDED_PLAY_SPELLING;
        case LONG_PLAY:
          return LONG_PLAY_SPELLING;
        case SINGLE:
          return SINGLE_SPELLING;
        case NOT_FOUND:
          return NOT_FOUND_MESSAGE_SPELLING;
      }
      // should never happen
      throw new UnsupportedOperationException(
          String.format("The spelling for the known record type %s isn't implemented.", this));
    }
  }
}
