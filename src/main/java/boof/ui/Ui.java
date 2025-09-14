package boof.ui;

import java.util.Scanner;

/**
 * Handles user interactions and displays messages for Boof.
 */

public class Ui {
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads a command from the user input.
     *
     * @return The user input command as a String
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays message in the UI to the user.
     * @param message
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays a message with a divider in the UI to the user.
     * @param message
     */
    public String displayMessageWithDivider(String message) {
        String s = "    -------------------------\n"
            + message + "\n"
            + "    -------------------------";
        System.out.println(s);
        return s;
    }

    /**
     * Displays a welcome message in the UI to the user.
     */
    public String showWelcome() {
        return "Hello! I'm Boof\nWhat can I do for you?\n"
            + "Please enter your command, or type 'help' to see available commands.";
    }

    /**
     * Displays an exit message in the UI to the user.
     */
    public void showExit() {
        displayMessageWithDivider("      Bye. Hope to see you again soon!");
    }

    /**
     * Displays an error message in the UI to the user.
     *
     * @param message Shows the error message to the user
     */
    public void showError(String message) {
        displayMessageWithDivider("      OOPS!!! " + message);
    }

    /**
     * Displays the help message in the UI to the user.
     */
    public String showHelp() {
        String helpMessage = "Here are the available commands:\n"
            + "1. list - Displays all tasks.\n"
            + "2. todo <description> - Adds a todo task.\n"
            + "3. deadline <description> /by <date/time> - Adds a deadline task.\n"
            + "4. event <description> /from <start date/time> /to <end date/time> - Adds an event task.\n"
            + "5. mark <task number> - Marks a task as done.\n"
            + "6. unmark <task number> - Marks a task as not done.\n"
            + "7. delete <task number> - Deletes a task.\n"
            + "8. find <keyword> - Finds tasks containing the keyword.\n"
            + "9. bye - Exits the application.\n"
            + "10. help - Displays this help message.";
        return displayMessageWithDivider(helpMessage);
    }
}
