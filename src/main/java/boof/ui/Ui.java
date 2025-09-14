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
            + "Please enter your commands (todo, deadline, event, list, mark, unmark, delete)";
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
}

