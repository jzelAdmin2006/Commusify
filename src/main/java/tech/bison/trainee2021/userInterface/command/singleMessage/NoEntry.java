package tech.bison.trainee2021.userInterface.command.singleMessage;

public class NoEntry extends SingleMessage {
  private static final String NO_ENTRY_REACTION = "";

  public NoEntry() {
    super(NO_ENTRY_REACTION);
  }

  @Override
  public String getArgumentDescription() {
    return "";
  }

  @Override
  public boolean loginIsRequired() {
    return false;
  }
}
