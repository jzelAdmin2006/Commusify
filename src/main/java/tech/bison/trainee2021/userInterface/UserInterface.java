package tech.bison.trainee2021.userInterface;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import tech.bison.trainee2021.userInterface.command.Command;
import tech.bison.trainee2021.userInterface.command.CommandFactory;
import tech.bison.trainee2021.userInterface.command.CommandFactory.KnownCommand;

public class UserInterface {
  private static final String COMMAND_ARGUMENT_DELIMITER = " ";
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
    String[] doubleQuotesSplit = input.split("\"");
    List<String> splitInput = new ArrayList<>();
    for (int i = 0; i < doubleQuotesSplit.length; i++) {
      if (i % 2 == 0) {
        splitInput.addAll(Arrays.asList(doubleQuotesSplit[i].split(COMMAND_ARGUMENT_DELIMITER)));
      } else {
        splitInput.add(doubleQuotesSplit[i]);
      }
    }
    String command = splitInput.get(0);
    List<String> commandArguments = new ArrayList<>();
    if (splitInput.size() > 1) {
      commandArguments.addAll(splitInput.subList(1, splitInput.size()));
    }
    Command commandExecutor = CommandFactory.create(command);
    return commandExecutor.execute(commandArguments);
  }

  public String exit() {
    isRunning = false;
    return "Terminating...";
  }
}
