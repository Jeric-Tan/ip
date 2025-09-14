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
import boof.exception.InvalidCommandException;
import boof.exception.ParametersException;
import boof.exception.TaskException;

/**
 * Parses user input and returns the corresponding Command object.
 */
public class Parser {
    /**
     * Enum representing the different command types.
     */
    public enum CommandType {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, FIND, UNKNOWN
    }

    /**
     * Determines the command type from the user input.
     *
     * @param input the user input
     * @return the CommandType
     */
    public static CommandType getCommandType(String input) {
        if (input == null || input.trim().isEmpty()) {
            return CommandType.UNKNOWN;
        }
        String command = input.toLowerCase().trim();
        assert command != null;
        if (command.equals("bye")) {
            return CommandType.BYE;
        } else if (command.equals("list")) {
            return CommandType.LIST;
        } else if (command.startsWith("mark")) {
            return CommandType.MARK;
        } else if (command.startsWith("unmark")) {
            return CommandType.UNMARK;
        } else if (command.startsWith("todo")) {
            return CommandType.TODO;
        } else if (command.startsWith("deadline")) {
            return CommandType.DEADLINE;
        } else if (command.startsWith("event")) {
            return CommandType.EVENT;
        } else if (command.startsWith("delete")) {
            return CommandType.DELETE;
        } else if (command.startsWith("find")) {
            return CommandType.FIND;
        } else {
            return CommandType.UNKNOWN;
        }
    }

    /**
     * Parses the user input into a Command object.
     *
     * @param input the user input
     * @return the Command object
     * @throws InvalidCommandException if the command is invalid
     * @throws ParametersException     if the parameters are invalid
     * @throws TaskException           if there is an error with the task
     */
    public static Command parse(String input) throws InvalidCommandException, ParametersException, TaskException {
        CommandType commandType = getCommandType(input);
        switch (commandType) {
        case BYE:
            return new ByeCommand();
        case LIST:
            return new ListCommand();
        case MARK:
            int markIndex = parseIndex(input, "\n[!] Format: mark <index>") - 1;
            return new MarkCommand(markIndex);
        case UNMARK:
            int unmarkIndex = parseIndex(input, "\n[!] Format: unmark <index>") - 1;
            return new UnmarkCommand(unmarkIndex);
        case TODO:
            String todoDescription = parseArgument(input, 5, "\n[!] Format: todo <description>");
            return new TodoCommand(todoDescription);
        case DEADLINE:
            String[] deadlineParts = parseDeadlineCommand(input, "\n[!] Format: deadline <description> /by <time>");
            return new DeadlineCommand(deadlineParts[0], deadlineParts[1]);
        case EVENT:
            String[] eventParts = parseEventCommand(input, "\n[!] Format: event <description> /from <time> /to <time>");
            return new EventCommand(eventParts[0], eventParts[1], eventParts[2]);
        case DELETE:
            int deleteIndex = parseIndex(input, "\n[!] Format: delete <index>") - 1;
            return new DeleteCommand(deleteIndex);
        case FIND:
            String findKeyword = parseArgument(input, 5, "\n[!] Format: find <keyword>");
            return new FindCommand(findKeyword);
        default:
            throw new InvalidCommandException(input);
        }
    }

    /**
     * Parses the index from the user input after the command.
     *
     * @param input the user input
     * @return the index
     * @throws TaskException       if the index is not a valid number
     * @throws ParametersException if no index is provided
     */
    public static int parseIndex(String input, String errorMessage) throws TaskException, ParametersException {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2) {
            throw new ParametersException(errorMessage);
        }
        assert parts[1].trim() != null;
        try {
            return Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException e) {
            throw new TaskException(parts[1].trim());
        }
    }

    /**
     * Parses the argument from the user input after the command.
     *
     * @param input         the user input
     * @param commandLength the length of the command
     * @param errorMessage  the error message to throw if the argument is empty
     * @return the argument
     * @throws ParametersException if the argument is empty
     */
    private static String parseArgument(String input,
        int commandLength, String errorMessage) throws ParametersException {

        if (input.length() <= commandLength || input.substring(commandLength).trim().isEmpty()) {
            throw new ParametersException(errorMessage);
        }
        return input.substring(5).trim();
    }

    /**
     * Parses the description and time of a deadline from the user input.
     *
     * @param input the user input
     * @return an array containing the description and time
     * @throws ParametersException if the description or time is empty
     */
    public static String[] parseDeadlineCommand(String input, String errorMessage) throws ParametersException {
        String[] parts = input.split(" ", 2);
        if (parts.length == 1 || parts[1].trim().isEmpty()) {
            throw new ParametersException(errorMessage);
        }
        assert parts.length > 1;
        String[] descriptionAndTime = parts[1].split("/by ", 2);
        String description = descriptionAndTime[0].trim();
        String byTime = descriptionAndTime.length > 1 ? descriptionAndTime[1].trim() : "";
        if (description.isEmpty()) {
            throw new ParametersException(errorMessage);
        }

        if (byTime.isEmpty()) {
            throw new ParametersException("\n [!] The /by time of a deadline cannot be empty.");
        }
        return new String[] { description, byTime };
    }

    /**
     * Parses the description, start time, and end time of an event from the user input.
     *
     * @param input the user input
     * @return an array containing the description, start time, and end time
     * @throws ParametersException if the description or time is empty
     */
    public static String[] parseEventCommand(String input, String errorMessage) throws ParametersException {
        String[] parts = input.split(" ", 2);
        if (parts.length == 1 || parts[1].trim().isEmpty()) {
            throw new ParametersException(errorMessage);
        }
        assert parts.length > 1;
        String[] descriptionAndTime = parts[1].split("/from ", 2);
        String description = descriptionAndTime[0].trim();
        if (description.isEmpty()) {
            throw new ParametersException(errorMessage);
        }
        if (descriptionAndTime.length < 2 || descriptionAndTime[1].trim().isEmpty()) {
            throw new ParametersException("\n [!] Missing /from time.");
        }

        String time = descriptionAndTime.length > 1 ? descriptionAndTime[1].trim() : "";
        String[] startAndEnd = time.split("/to ", 2);
        String from = startAndEnd[0].trim();
        String to = startAndEnd.length > 1 ? startAndEnd[1].trim() : "";

        if (from.isEmpty()) {
            throw new ParametersException("\n [!] The /from time of an event cannot be empty.");
        }
        if (to.isEmpty()) {
            throw new ParametersException("\n [!] The /to time of an event cannot be empty.");
        }

        return new String[] { description, from, to };
    }
}
