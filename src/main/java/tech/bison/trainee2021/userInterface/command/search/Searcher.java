package tech.bison.trainee2021.userInterface.command.search;

import java.util.List;

public interface Searcher {
  public List<Searchable> search(String search);
}
