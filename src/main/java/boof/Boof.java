package boof;

import boof.command.Command;
import boof.exception.BoofException;
import boof.parser.Parser;
import boof.storage.Storage;
import boof.task.TaskList;
import boof.ui.Ui;

/**
 * The main class.
 */
public class Boof {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Default constructor which creates a new Boof instance with default file path './data/boof.txt'.
     */
    public Boof() {
        this("./data/boof.txt");
    }

    /**
     * Constructor which creates a new Boof instance. If no filePath is provided,
     * creates a data file at './data/boof.txt'.
     *
     * @param filePath the optional file path for storage
     */
    public Boof(String filePath) {
        storage = new Storage(filePath);
        ui = new Ui();
        tasks = new TaskList(storage.loadTasks());

        assert storage != null;
        assert tasks != null;
        assert ui != null;
    }

    /**
     * Starts the main program loop. The loop uses the UI, storage, and task list.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            String userText = ui.readCommand();
            String response = getResponse(userText);
            ui.showMessage(response);
            if (Parser.getCommandType(userText) == Parser.CommandType.BYE) {
                isExit = true;
            }
        }
    }

    /**
     * Gets the response to a user input command.
     *
     * @param input the user input command
     * @return the response message
     */
    public String getResponse(String input) {
        try {
            Command command = Parser.parse(input);
            return command.execute(storage, tasks, ui);
        } catch (BoofException e) {
            return e.getMessage();
        }
    }

    public String getWelcomeMessage() {
        return ui.showWelcome();
    }


    /**
     * The main method to start the Boof application.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        new Boof("./data/boof.txt").run();
    }
}
