package tech.bison.trainee2021.userInterface;

import java.rmi.UnexpectedException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import tech.bison.trainee2021.playable.Playable;
import tech.bison.trainee2021.playable.UnavailablePlayable;
import tech.bison.trainee2021.structure.GuestUser;
import tech.bison.trainee2021.structure.User;
import tech.bison.trainee2021.userInterface.command.Command;
import tech.bison.trainee2021.userInterface.command.CommandFactory;
import tech.bison.trainee2021.userInterface.command.CommandFactory.KnownCommand;
import tech.bison.trainee2021.userInterface.util.CommandLine;
import tech.bison.trainee2021.userInterface.command.MissingAuthentification;

public class UserInterface {
  public static final String EXIT = "/exit";
  private static User currentUser = new GuestUser();
  private static Playable currentPlayable = new UnavailablePlayable();
  private boolean isRunning;

  public UserInterface() {
    isRunning = true;
  }

  public void run() {
    boolean isFirstTime = true;
    try (Scanner scanner = new Scanner(System.in)) {
      while (isRunning) {
        try {
          String input = KnownCommand.WELCOME.spelling();
          if (isFirstTime) {
            isFirstTime = false;
          } else {
            input = scanner.nextLine();
          }
          System.out.println(generateOutput(input));
        } catch (Exception e) {
          Exception unexpectedException = new UnexpectedException("An unexpected exception occured.");
          unexpectedException.printStackTrace();
          e.printStackTrace();
        }
      }
    }
  }

  public String generateOutput(String input) {
    if (input.equals(EXIT)) {
      return exit();
    }
    String[] splitInput = CommandLine.translateCommandline(input);
    String command = splitInput[0];
    List<String> commandArguments = Arrays.asList(Arrays.copyOfRange(splitInput, 1, splitInput.length));
    Command commandExecutor = CommandFactory.create(command);
    if (commandExecutor.loginIsRequired() && !getCurrentUser().isLoggedIn()) {
      return new MissingAuthentification(command).execute(commandArguments);
    } else {
      return commandExecutor.execute(commandArguments);
    }
  }

  public String exit() {
    isRunning = false;
    return "Terminating...";
  }

  public static User getCurrentUser() {
    return currentUser;
  }

  public static void setCurrentUser(User currentUser) {
    UserInterface.currentUser = currentUser;
  }

  public static Playable getCurrentPlayable() {
    return currentPlayable;
  }

  public static void setCurrentPlayable(Playable currentPlayable) {
    UserInterface.currentPlayable = currentPlayable;
  }
}
