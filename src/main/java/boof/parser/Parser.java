package boof.parser;
public class Parser {
  public static String getCommand(String userInput) {
    String[] parts = userInput.trim().split(" ", 2);
    return parts[0];
  }

}
