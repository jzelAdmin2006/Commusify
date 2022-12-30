package tech.bison.trainee2021.playable.specificPlayableList.albumType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import tech.bison.trainee2021.playable.Playable;
import tech.bison.trainee2021.playable.Track;
import tech.bison.trainee2021.playable.specificPlayableList.Album;
import tech.bison.trainee2021.structure.Artist;

public abstract class Record extends Album {
  /**
   * A record album can't contain unlimited tracks, so there's a limit
   * 
   * @return Max number of tracks the record album can contain
   */
  protected abstract int limit();

  /**
   * This constructor creates a new record album and writes it into the Commusify database
   * 
   * @param title
   *          The title of the new record
   * @param tracks
   *          The tracks of the new record
   * @param interpreters
   *          The interpreters of the new record
   */
  public Record(String title, List<Track> tracks, List<Artist> interpreters) {
    super(title, tracks, interpreters);
  }

  /**
   * This constructor reads the existing record with the given ID from the Commusify
   * database
   * 
   * @param id
   *          The ID of the existing record
   */
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

    /**
     * @return All the record type spellings formatted sensibly
     */
    public static String getSpellings() {
      return Arrays.asList(KnownRecordType.values())
          .stream()
          .map(knownRecordType -> knownRecordType.spelling())
          .collect(Collectors.toList())
          .stream()
          .map(String::valueOf)
          .collect(Collectors.joining(" / "));
    }

    /**
     * @param Spelling
     *          Of the record type
     * @return Known record type with this spelling
     */
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
