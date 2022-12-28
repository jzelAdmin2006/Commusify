package tech.bison.trainee2021.userInterface.command.singleMessage;

import java.util.List;

public class Welcome extends SingleMessage {

  public Welcome() {
    super("Welcome to ...\n\n   ____   U  ___ u  __  __    __  __     _   _   ____                  _____  __   __ \r\n"
        + "U /\"___|   \\/\"_ \\/U|' \\/ '|uU|' \\/ '|uU |\"|u| | / __\"| u      ___     |\" ___| \\ \\ / / \r\n"
        + "\\| | u     | | | |\\| |\\/| |/\\| |\\/| |/ \\| |\\| |<\\___ \\/      |_\"_|   U| |_  u  \\ V /  \r\n"
        + " | |/__.-,_| |_| | | |  | |  | |  | |   | |_| | u___) |       | |    \\|  _|/  U_|\"|_u \r\n"
        + "  \\____|\\_)-\\___/  |_|  |_|  |_|  |_|  <<\\___/  |____/>>    U/| |\\u   |_|       |_|   \r\n"
        + " _// \\\\      \\\\   <<,-,,-.  <<,-,,-.  (__) )(    )(  (__).-,_|___|_,-.)(\\\\,-.-,//|(_  \r\n"
        + "(__)(__)    (__)   (./  \\.)  (./  \\.)     (__)  (__)      \\_)-' '-(_/(__)(_/ \\_) (__) \n\n");
  }

  @Override
  public String execute(List<String> arguments) {
    return super.execute(arguments) + "\n" + new ShowAllCommands().execute(arguments);
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
