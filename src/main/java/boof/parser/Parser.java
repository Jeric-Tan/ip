package boof.parser;

/**
 * Parses user input into command types and parameters.
 */
public class Parser {
  public enum CommandType {
    BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, FIND, UNKNOWN
  }

  /**
   * Gets the command type from the user input.
   * @param input the user input
   * @return the command type
   */
  public static CommandType getCommandType(String input) {
    if (input == null || input.trim().isEmpty()) {
      return CommandType.UNKNOWN;
    }
    String command = input.toLowerCase().trim();
    if (command.equals("bye")) {
      return CommandType.BYE;
    } else if (command.equals("list")) {
      return CommandType.LIST;
    } else if (command.startsWith("mark ")) {
      return CommandType.MARK;
    } else if (command.startsWith("unmark ")) {
      return CommandType.UNMARK;
    } else if (command.startsWith("todo ")) {
      return CommandType.TODO;
    } else if (command.startsWith("deadline ")) {
      return CommandType.DEADLINE;
    } else if (command.startsWith("event ")) {
      return CommandType.EVENT;
    } else if (command.startsWith("delete ")) {
      return CommandType.DELETE;
    } else if (command.startsWith("find ")) {
      return CommandType.FIND;
    } else {
      return CommandType.UNKNOWN;
    }
  }

  /**
   * Parses the index from the user input.
   * @param input the user input
   * @return the index
   * @throws NumberFormatException if the index is not a valid number
   */
  public static int parseIndex(String input) throws NumberFormatException {
    String[] parts = input.split(" ", 2);
    if (parts.length < 2) {
      throw new NumberFormatException("No index provided");
    }
    return Integer.parseInt(parts[1].trim());
  }

  /**
   * Parses the description of a todo from the user input.
   * @param input the user input
   * @return the todo description
   * @throws IllegalArgumentException if the description is empty
   */
  public static String parseTodoDescription(String input) throws IllegalArgumentException {
    if (input.length() <= 4 || input.substring(5).trim().isEmpty()) {
      throw new IllegalArgumentException("The description of a todo cannot be empty.");
    }
    return input.substring(5).trim();
  }

  /**
   * Parses the description and time of a deadline from the user input.
   * @param input the user input
   * @return an array containing the description and time
   * @throws IllegalArgumentException if the description or time is empty
   */
  public static String[] parseDeadlineCommand(String input) throws IllegalArgumentException {
    String[] parts = input.split(" ", 2);
    if (parts.length == 1 || parts[1].trim().isEmpty()) {
      throw new IllegalArgumentException("The description of a deadline cannot be empty.");
    }
    String[] descriptionAndTime = parts[1].split("/by ", 2);
    String description = descriptionAndTime[0].trim();
    String byTime = descriptionAndTime.length > 1 ? descriptionAndTime[1].trim() : "";
    if (description.isEmpty()) {
      throw new IllegalArgumentException("The description of a deadline cannot be empty.");
    }
    return new String[] { description, byTime };
  }

  /**
   * Parses the description and time of an event from the user input.
   * @param input the user input
   * @return an array containing the description, start time, and end time
   * @throws IllegalArgumentException if the description or time is empty
   */
  public static String[] parseEventCommand(String input) throws IllegalArgumentException {
    String[] parts = input.split(" ", 2);
    if (parts.length == 1 || parts[1].trim().isEmpty()) {
      throw new IllegalArgumentException("The description of an event cannot be empty.");
    }
    String[] descriptionAndTime = parts[1].split("/from ", 2);
    String description = descriptionAndTime[0].trim();
    String time = descriptionAndTime.length > 1 ? descriptionAndTime[1].trim() : "";
    String[] startAndEnd = time.split("/to ", 2);
    String from = startAndEnd[0].trim();
    String to = startAndEnd.length > 1 ? startAndEnd[1].trim() : "";
    if (description.isEmpty()) {
      throw new IllegalArgumentException("The description of an event cannot be empty.");
    }
    return new String[] { description, from, to };
  }
}
