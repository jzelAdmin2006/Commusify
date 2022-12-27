package tech.bison.trainee2021.userInterface;

import java.rmi.UnexpectedException;
import java.util.Arrays;
import java.util.Scanner;

import tech.bison.trainee2021.userInterface.command.Command;
import tech.bison.trainee2021.userInterface.command.CommandFactory;
import tech.bison.trainee2021.userInterface.command.CommandFactory.KnownCommand;

public class UserInterface {
  private static final String COMMAND_DELIMITER = " ";
  public static final String EXIT = "/exit";
  private boolean isRunning;

  public UserInterface() {
    isRunning = true;
  }

  public void run() {
    String input = KnownCommand.WELCOME.spelling();
    try (Scanner scanner = new Scanner(System.in)) {
      while (isRunning) {
        try {
          System.out.println(generateOutput(input));
          input = scanner.nextLine();
        } catch (Exception e) {
          Exception unexpectedException = new UnexpectedException("An unexpected exception occured.");
          unexpectedException.printStackTrace();
          e.printStackTrace();
          input = KnownCommand.NO_ENTRY.spelling();
        }
      }
    }
  }

  public String generateOutput(String input) {
    if (input.equals(EXIT)) {
      return exit();
    }
    String[] splitInput = input.split(COMMAND_DELIMITER);
    String command = splitInput[0];
    String[] commandArguments = extractCommandArguments(splitInput);
    Command commandExecutor = CommandFactory.create(command);
    return commandExecutor.execute(commandArguments);
  }

  public String[] extractCommandArguments(String[] splitInput) {
    String[] commandArguments = new String[splitInput.length - 1];
    if (splitInput.length > 1) {
      commandArguments = Arrays.copyOfRange(splitInput, 1, splitInput.length - 1);
    }
    return commandArguments;
  }

  public String exit() {
    isRunning = false;
    return "Terminating...";
  }
}
