package tech.bison.trainee2021.userInterface.command.create;

import static tech.bison.trainee2021.userInterface.util.NumericChecker.isNumeric;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import tech.bison.trainee2021.framework.playable.Playable;
import tech.bison.trainee2021.framework.playable.Track;
import tech.bison.trainee2021.framework.playable.UnavailablePlayable;
import tech.bison.trainee2021.framework.playable.Track.TrackIdChecker;
import tech.bison.trainee2021.framework.playable.specificPlayableList.albumType.Record.KnownRecordType;
import tech.bison.trainee2021.framework.playable.specificPlayableList.albumType.recordType.DoubleLongPlay;
import tech.bison.trainee2021.framework.playable.specificPlayableList.albumType.recordType.ExtendedPlay;
import tech.bison.trainee2021.framework.playable.specificPlayableList.albumType.recordType.LongPlay;
import tech.bison.trainee2021.framework.playable.specificPlayableList.albumType.recordType.Single;
import tech.bison.trainee2021.framework.structure.Artist;
import tech.bison.trainee2021.framework.structure.Artist.ArtistIdChecker;
import tech.bison.trainee2021.userInterface.UserInterface;
import tech.bison.trainee2021.userInterface.command.argumentExpectation.MinimumArgumentAmountExpectation;

public class CreateRecordAlbum implements MinimumArgumentAmountExpectation {

  @Override
  public String proceed(List<String> arguments) {
    String trackId = arguments.get(2);
    if (isNumeric(trackId)) {
      return processNextArgument(arguments, Integer.parseInt(trackId));
    } else {
      return String.format("\"%s\" is not a track ID (a track  ID should be a number).", trackId);
    }
  }

  private String processNextArgument(List<String> arguments, int trackId) {
    List<String> interpreterIds = arguments.subList(3, arguments.size());
    for (String interpreterId : interpreterIds) {
      if (!isNumeric(interpreterId)) {
        return String.format("\"%s\" is not an artist ID (an artist ID should be a number).", interpreterId);
      }
    }
    return createAlbumWhenArgumentsChecked(arguments,
        trackId,
        interpreterIds.stream().map(interpreterId -> Integer.parseInt(interpreterId)).collect(Collectors.toList()));
  }

  private String createAlbumWhenArgumentsChecked(List<String> arguments, int trackId, List<Integer> interpreterIds) {
    if (new TrackIdChecker().exists(trackId)) {
      return checkNextArgument(arguments, new Track(trackId), interpreterIds);
    } else {
      return String.format("The track ID %s wan't found.", trackId);
    }
  }

  private String checkNextArgument(List<String> arguments, Track track, List<Integer> interpreterIds) {
    for (int interpreterId : interpreterIds) {
      if (!new ArtistIdChecker().exists(interpreterId)) {
        return String.format("The artist ID %s wan't found.", interpreterId);
      }
    }
    Artist artistUserClaimsToBe = new Artist(interpreterIds.get(0));
    if (UserInterface.getCurrentUser().isArtistMember(artistUserClaimsToBe)) {
      return createAlbum(arguments,
          Collections.singletonList(track),
          interpreterIds.stream().map(interpreterId -> new Artist(interpreterId)).collect(Collectors.toList()));
    } else {
      return String.format("You aren't a member of the artist with the id \"%s\"", artistUserClaimsToBe.getId());
    }
  }

  protected String createAlbum(List<String> arguments, List<Track> track, List<Artist> artist) {
    String recordType = arguments.get(1);
    Playable album = new UnavailablePlayable();
    String title = arguments.get(0);
    switch (KnownRecordType.translate(recordType)) {
      case NOT_FOUND:
        return String.format("The record type \"%s\" wasn't found.", recordType);
      case DOUBLE_LONG_PLAY:
        album = new DoubleLongPlay(title, track, artist);
      case EXTENDED_PLAY:
        album = new ExtendedPlay(title, track, artist);
      case LONG_PLAY:
        album = new LongPlay(title, track, artist);
      case SINGLE:
        album = new Single(title, track.get(0), artist);
      default:
        return String.format("Record was created with id %s.", album.getId());
    }
  }

  @Override
  public String getArgumentDescription() {
    return String.format(
        "[title] [type (%s)] [initializer track ID] [ID of interpreter artist which has you as a member] {[Optional: co-interpreter artist ID], [Optional: co-interpreter artist ID], ...}",
        KnownRecordType.getSpellings());
  }

  @Override
  public int getMinimumNumberOfArguments() {
    return 4;
  }
}
