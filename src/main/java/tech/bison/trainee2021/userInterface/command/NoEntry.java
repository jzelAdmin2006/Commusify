package tech.bison.trainee2021.userInterface.command;

public class NoEntry implements Command {

  private static final String NO_ENTRY_REACTION = "";

  @Override
  public String execute(String[] arguments) {
    return NO_ENTRY_REACTION;
  }
}
