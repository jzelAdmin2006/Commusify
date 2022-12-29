package tech.bison.trainee2021.userInterface.command.simpleMessage;

public class NoEntry extends SimpleMessage {
  private static final String NO_ENTRY_REACTION = "";

  public NoEntry() {
    super(NO_ENTRY_REACTION);
  }

  @Override
  public String getArgumentDescription() {
    return "";
  }
}
