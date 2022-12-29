package tech.bison.trainee2021.playable;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import tech.bison.trainee2021.Commusify;
import tech.bison.trainee2021.userInterface.command.search.Searchable;
import tech.bison.trainee2021.userInterface.command.search.Searcher;

public class PlayableList extends AvailablePlayable {

  private String title;
  private final List<Playable> playables = new ArrayList<>();
  private final int id;
  private int currentPlayableIndex;
  private boolean loopIsOn;
  private boolean shuffleIsOn;
  private List<Integer> playableIndexHistory;
  private int currentPlayableIndexHistoryIndex;
  private Random random = new Random();

  @Override
  public int hashCode() {
    return Objects.hash(id, title, playables);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PlayableList other = (PlayableList) obj;
    return id == other.id && Objects.equals(title, other.title) && Objects.equals(playables, other.playables);
  }

  public PlayableList(String title, List<Playable> playables) {
    this.id = create(title, playables);
    this.title = title;
  }

  private int create(String title, List<Playable> playables) {
    int id = 0;
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall("{call SP_CREATE_PLAYABLE_LIST(?)}");
      callableStatement.setString("Title", title);
      ResultSet result = callableStatement.executeQuery();

      result.next();
      id = result.getInt("ID");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    addPlayables(id, playables);
    return id;
  }

  protected void addPlayables(int id, List<Playable> playables) {
    for (Playable playable : playables) {
      addPlayable(id, playable);
    }
  }

  private void addPlayable(int id, Playable playable) {
    playables.add(playable);
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall("{call SP_ADD_PLAYABLE_LIST_PLAYABLE(?, ?, ?)}");
      callableStatement.setInt("PlayableListID", id);
      callableStatement.setInt("PlayableID", playable.getId());
      callableStatement.setBoolean("IsTrack", playable.isTrack());
      callableStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public PlayableList(int id) {
    this.id = id;
    find(id);
  }

  private void find(int id) {
    try {
      Connection connection = DriverManager.getConnection(Commusify.DATABASE);
      CallableStatement callableStatement = connection.prepareCall("{call SP_FIND_PLAYABLE_LIST(?)}");
      callableStatement.setInt("ID", id);
      ResultSet result = callableStatement.executeQuery();

      result.next();
      title = result.getString("Title");
      do {
        if (result.getInt("FK_TrackID") == Commusify.INVALID_ID) {
          playables.add(new PlayableList(result.getInt("FK_ContainedPlayableListID")));
        } else if (result.getInt("FK_ContainedPlayableListID") == Commusify.INVALID_ID) {
          playables.add(new Track(result.getInt("FK_TrackID")));
        } else {
          throw new SQLException(String.format(
              "The playableListPlayable with the ID %s could not be found. This might be because of data corruption.",
              result.getInt("PlayableListPlayableID")));
        }
      } while (result.next());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public String getTitle() {
    return title;
  }

  public List<Playable> getPlayables() {
    return Collections.unmodifiableList(playables);
  }

  private List<Track> getTracks() {
    List<Track> tracks = new ArrayList<>();
    for (Playable playable : playables) {
      if (playable.getClass().isAssignableFrom(getClass())) {
        PlayableList playableList = (PlayableList) playable;
        tracks.addAll(playableList.getTracks());
      } else {
        tracks.add((Track) playable);
      }
    }
    return tracks;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void play() {
    currentPlayableIndex = 0;
    getTracks().get(currentPlayableIndex).play();
  }

  @Override
  public String playNext() {
    List<Track> tracks = getTracks();
    if (currentPlayableIndexHistoryIndex < playableIndexHistory.size() - 1) {
      currentPlayableIndexHistoryIndex++;
      currentPlayableIndex = playableIndexHistory.get(currentPlayableIndexHistoryIndex);
    } else if (shuffleIsOn) {
      currentPlayableIndex = random.nextInt(tracks.size());
      playableIndexHistory.add(currentPlayableIndex);
    } else if (currentPlayableIndex < tracks.size() - 1) {
      currentPlayableIndex++;
      currentPlayableIndexHistoryIndex++;
      playableIndexHistory.add(currentPlayableIndex);
    } else if (loopIsOn) {
      currentPlayableIndex = 0;
      currentPlayableIndexHistoryIndex++;
      playableIndexHistory.add(currentPlayableIndex);
    } else {
      return "This playable list has no next track and it's not looping.";
    }
    tracks.get(currentPlayableIndex).play();
    return "Playing next track now...";
  }

  @Override
  public void playPrevious() {
    List<Track> tracks = getTracks();
    if (currentPlayableIndexHistoryIndex > 0) {
      currentPlayableIndexHistoryIndex--;
      currentPlayableIndex = playableIndexHistory.get(currentPlayableIndexHistoryIndex);
    } else if (shuffleIsOn) {
      currentPlayableIndex = random.nextInt(tracks.size());
      playableIndexHistory.add(0, currentPlayableIndex);
    } else if (currentPlayableIndex > 0) {
      currentPlayableIndex--;
      playableIndexHistory.add(0, currentPlayableIndex);
    } else if (loopIsOn) {
      currentPlayableIndex = tracks.size() - 1;
      playableIndexHistory.add(0, currentPlayableIndex);
    }
    tracks.get(currentPlayableIndex).play();
  }

  @Override
  public String shuffle(boolean shuffleIsOn) {
    String message;
    if (shuffleIsOn && !this.shuffleIsOn) {
      message = "Shuffle is on now.";
    } else if (!shuffleIsOn && this.shuffleIsOn) {
      message = "Shuffle is off now.";
    } else if (shuffleIsOn && this.shuffleIsOn) {
      message = "Shuffle is already on.";
    } else {
      message = "Shuffle is already off.";
    }
    this.shuffleIsOn = shuffleIsOn;
    return message;
  }

  @Override
  public void loop(boolean loopIsOn) {
    this.loopIsOn = loopIsOn;
  }

  @Override
  public void download() {
    for (Playable playable : playables) {
      playable.download();
    }
  }

  @Override
  public boolean isTrack() {
    return false;
  }

  @Override
  public String result() {
    return String
        .format("PlayableList: ID = %s, title = \"%s\", number of playables = %s", id, title, playables.size());
  }

  static class PlayableListSearcher extends Searcher {

    @Override
    public String getSearchCallSP() {
      return "SP_SEARCH_PLAYABLE_LIST";
    }

    @Override
    public Searchable of(int id) {
      return new PlayableList(id);
    }
  }

  static boolean idExists(int id) {
    return false;
  }
}
