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
     * Displays a horizontal line in the UI to the user.
     */

    public void displayLine() {
        System.out.println("    -------------------------");
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
    public String showExit() {
        displayLine();
        String message = "      Bye. Hope to see you again soon!";
        displayLine();
        return message;
    }

    /**
     * Displays an error message in the UI to the user.
     *
     * @param message Shows the error message to the user
     */
    public void showError(String message) {
        displayLine();
        System.out.println("      OOPS!!! " + message);
        displayLine();
    }

    /**
     * Displays a loading error message in the UI to the user.
     */
    public void showLoadingError() {
        showError("Error loading tasks from file.");
    }

    /**
     * Displays a message in the UI to the user.
     *
     * @param message The message to be displayed
     */
    public void showMessage(String message) {
        System.out.println(message);
    }
}
