package tech.bison.trainee2021.userInterface.util;

import java.util.regex.Pattern;

public class NumericChecker {

  public static boolean isNumeric(String string) {
    if (string == null) {
      return false;
    }
    return Pattern.compile("-?\\d+(\\.\\d+)?").matcher(string).matches();
  }
}
