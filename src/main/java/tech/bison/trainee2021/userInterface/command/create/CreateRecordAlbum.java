package tech.bison.trainee2021.userInterface.command.create;

import static tech.bison.trainee2021.userInterface.util.NumericChecker.isNumeric;

import java.util.Collections;
import java.util.List;

import tech.bison.trainee2021.playable.Playable;
import tech.bison.trainee2021.playable.Track;
import tech.bison.trainee2021.playable.Track.TrackIdChecker;
import tech.bison.trainee2021.playable.UnavailablePlayable;
import tech.bison.trainee2021.playable.specificPlayableList.albumType.Record.KnownRecordType;
import tech.bison.trainee2021.playable.specificPlayableList.albumType.recordType.DoubleLongPlay;
import tech.bison.trainee2021.playable.specificPlayableList.albumType.recordType.ExtendedPlay;
import tech.bison.trainee2021.playable.specificPlayableList.albumType.recordType.LongPlay;
import tech.bison.trainee2021.playable.specificPlayableList.albumType.recordType.Single;
import tech.bison.trainee2021.structure.Artist;
import tech.bison.trainee2021.structure.Artist.ArtistIdChecker;
import tech.bison.trainee2021.userInterface.UserInterface;
import tech.bison.trainee2021.userInterface.command.argumentExpectation.MinimumArgumentAmountExpectation;

public class CreateRecordAlbum implements MinimumArgumentAmountExpectation {

  @Override
  public String proceed(List<String> arguments) {
    return processNextArgument(arguments);
  }

  public String processNextArgument(List<String> arguments) {
    String trackId = arguments.get(2);
    if (isNumeric(trackId)) {
      return processNextArgument(arguments, Integer.parseInt(trackId));
    } else {
      return String.format("\"%s\" is not a track ID (a track  ID should be a number).", trackId);
    }
  }

  private String processNextArgument(List<String> arguments, int trackId) {
    String interpreterId = arguments.get(3);
    if (isNumeric(interpreterId)) {
      return createTrackWhenArgumentsChecked(arguments, trackId, Integer.parseInt(interpreterId));
    } else {
      return String.format("\"%s\" is not an artist ID (an artist ID should be a number).", interpreterId);
    }
  }

  private String createTrackWhenArgumentsChecked(List<String> arguments, int trackId, int interpreterId) {
    if (new TrackIdChecker().exists(trackId)) {
      return checkNextArgument(arguments, new Track(trackId), interpreterId);
    } else {
      return String.format("The track ID %s wan't found.", trackId);
    }
  }

  private String checkNextArgument(List<String> arguments, Track track, int interpreterId) {
    if (new ArtistIdChecker().exists(interpreterId)) {
      Artist artistUserClaimsToBe = new Artist(interpreterId);
      if (UserInterface.getCurrentUser().isArtistMember(artistUserClaimsToBe)) {
        return createRecord(arguments, track, artistUserClaimsToBe);
      } else {
        return String.format("You aren't a member of the artist with the id \"%s\"", artistUserClaimsToBe.getId());
      }
    } else {
      return String.format("The artist ID %s wan't found.", interpreterId);
    }
  }

  private String createRecord(List<String> arguments, Track track, Artist artist) {
    String recordType = arguments.get(1);
    Playable album = new UnavailablePlayable();
    switch (KnownRecordType.translate(recordType)) {
      case NOT_FOUND:
        return String.format("The record type \"%s\" wasn't found.", recordType);
      case DOUBLE_LONG_PLAY:
        album = new DoubleLongPlay(arguments.get(0), Collections.singletonList(track),
            Collections.singletonList(artist));
      case EXTENDED_PLAY:
        album = new ExtendedPlay(arguments.get(0), Collections.singletonList(track), Collections.singletonList(artist));
      case LONG_PLAY:
        album = new LongPlay(arguments.get(0), Collections.singletonList(track), Collections.singletonList(artist));
      case SINGLE:
        album = new Single(arguments.get(0), Collections.singletonList(track), Collections.singletonList(artist));
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
