package tech.bison.trainee2021.userInterface.command;

public class Welcome extends SingleMessage {

  public Welcome() {
    super("Welcome to ...\n\n    ____                                    _  __       \r\n"
        + "  / ___|___  _ __ ___  _ __ ___  _   _ ___(_)/ _|_   _ \r\n"
        + " | |   / _ \\| '_ ` _ \\| '_ ` _ \\| | | / __| | |_| | | |\r\n"
        + " | |__| (_) | | | | | | | | | | | |_| \\__ \\ |  _| |_| |\r\n"
        + "  \\____\\___/|_| |_| |_|_| |_| |_|\\__,_|___/_|_|  \\__, |\r\n"
        + "                                                 |___/\n\n");
  }

  @Override
  public String execute(String[] arguments) {
    return super.execute(arguments) + "\n" + new ShowAllCommands().execute(arguments);
  }
}
