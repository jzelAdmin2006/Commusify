package tech.bison.trainee2021.userInterface.command;

import java.util.List;

public interface Command {
  String execute(List<String> arguments);
}
