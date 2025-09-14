package boof.parser;

import boof.command.ByeCommand;
import boof.command.Command;
import boof.command.DeadlineCommand;
import boof.command.DeleteCommand;
import boof.command.EventCommand;
import boof.command.FindCommand;
import boof.command.ListCommand;
import boof.command.MarkCommand;
import boof.command.TodoCommand;
import boof.command.UnmarkCommand;

/**
 * Parses user input into command types and parameters.
 */
public class Parser {
    /**
     * Enum representing the different command types.
     */
    public enum CommandType {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, FIND, UNKNOWN
    }

    /**
     * Gets the command type from the user input.
     *
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
     * Parses the user input and returns the corresponding Command object.
     *
     * @param input the user input
     * @return the corresponding Command object
     * @throws IllegalArgumentException if the command is unknown or parameters are invalid
     * @throws NumberFormatException    if an index parameter is not a valid number
     */
    public static Command parse(String input) throws IllegalArgumentException, NumberFormatException {
        CommandType commandType = getCommandType(input);
        switch (commandType) {
        case BYE:
            return new ByeCommand();
        case LIST:
            return new ListCommand();
        case MARK:
            int markIndex = parseIndex(input) - 1;
            return new MarkCommand(markIndex);
        case UNMARK:
            int unmarkIndex = parseIndex(input) - 1;
            return new UnmarkCommand(unmarkIndex);
        case TODO:
            String todoDescription = parseArgument(input, 5, "The description of a todo cannot be empty.");
            return new TodoCommand(todoDescription);
        case DEADLINE:
            String[] deadlineParts = parseDeadlineCommand(input);
            return new DeadlineCommand(deadlineParts[0], deadlineParts[1]);
        case EVENT:
            String[] eventParts = parseEventCommand(input);
            return new EventCommand(eventParts[0], eventParts[1], eventParts[2]);
        case DELETE:
            int deleteIndex = parseIndex(input) - 1;
            return new DeleteCommand(deleteIndex);
        case FIND:
            String findKeyword = parseArgument(input, 5, "The keyword for find cannot be empty.");
            return new FindCommand(findKeyword);
        default:
            throw new IllegalArgumentException("I don't know what that means.");
        }
    }


    /**
     * Parses the index from the user input.
     *
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
     *
     * @param input the user input
     * @return the description
     * @throws IllegalArgumentException if the description is empty
     */
    private static String parseArgument(String input, int commandLength, String errorMessage) {
        if (input.length() <= commandLength || input.substring(commandLength).trim().isEmpty()) {
            throw new IllegalArgumentException(errorMessage);
        }
        return input.substring(commandLength).trim();
    }

    /**
     * Parses the description and time of a deadline from the user input.
     *
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
     *
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
