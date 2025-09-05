package boof;

import boof.parser.Parser;
import boof.storage.Storage;
import boof.task.Deadline;
import boof.task.Event;
import boof.task.Task;
import boof.task.TaskList;
import boof.task.Todo;
import boof.ui.Ui;

/**
 * The main class.
 */
public class Boof {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructor which creates a new Boof instance.
     *
     * @param filePath the file path for storage
     */
    public Boof() {
        this("./data/boof.txt");
    }

    /**
     * Constructor which creates a new Boof instance with the specified file path.
     *
     * @param filePath the file path for storage
     */
    public Boof(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (Exception e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Starts the main program loop. The loop uses the UI, storage, and task list.
     */
    public void run() {
        
        boolean isExit = false;
        while (!isExit) {
            String userText = ui.readCommand();
            String response = processCommand(userText);
            ui.showMessage(response);
            if (Parser.getCommandType(userText) == Parser.CommandType.BYE) {
                isExit = true;
            }
        }
    }

    /**
     * Processes a single user command and returns the response as a String.
     */
    private String processCommand(String userText) {
        StringBuilder output = new StringBuilder();
        Parser.CommandType commandType = Parser.getCommandType(userText);
        try {
            switch (commandType) {
            case FIND: {
                String[] parts = userText.split(" ", 2);
                if (parts.length < 2 || parts[1].trim().isEmpty()) {
                    output.append("OOPS!!! Please provide a keyword to find.\n");
                    break;
                }
                String keyword = parts[1].trim().toLowerCase();
                output.append("Here are the matching tasks in your list:\n");
                int count = 1;
                for (int i = 0; i < tasks.size(); i++) {
                    Task t = tasks.get(i);
                    if (t.getDescription().toLowerCase().contains(keyword)) {
                        output.append(count + "." + t.toString() + "\n");
                        count++;
                    }
                }
                if (count == 1) {
                    output.append("(No matching tasks found)\n");
                }
                break;
            }
            case BYE:
                output.append("bye");
                break;
            case LIST:
                output.append("------- Your List -------\n");
                for (int i = 0; i < tasks.size(); i++) {
                    output.append((i + 1) + "." + tasks.get(i).toString() + "\n");
                }
                break;
            case MARK: {
                int taskIndex = Parser.parseIndex(userText) - 1;
                Task t = tasks.get(taskIndex);
                t.markAsDone();
                storage.save(tasks.getAll());
                output.append("Nice! I've marked this task as done:\n");
                output.append("  " + t.toString() + "\n");
                break;
            }
            case UNMARK: {
                int taskIndex = Parser.parseIndex(userText) - 1;
                Task t = tasks.get(taskIndex);
                t.unmarkAsDone();
                storage.save(tasks.getAll());
                output.append("OK, I've marked this task as not done yet:\n");
                output.append("  " + t.toString() + "\n");
                break;
            }
            case TODO: {
                String description = Parser.parseTodoDescription(userText);
                Task newTask = new Todo(description);
                tasks.add(newTask);
                storage.save(tasks.getAll());
                output.append("Got it. I've added this task:\n");
                output.append("  " + newTask.toString() + "\n");
                output.append("Now you have " + tasks.size() + " tasks in the list.\n");
                break;
            }
            case DEADLINE: {
                String[] deadlineParts = Parser.parseDeadlineCommand(userText);
                String description = deadlineParts[0];
                String byDate = deadlineParts[1];
                Task newTask = new Deadline(description, byDate);
                tasks.add(newTask);
                storage.save(tasks.getAll());
                output.append("Got it. I've added this task:\n");
                output.append("  " + newTask.toString() + "\n");
                output.append("Now you have " + tasks.size() + " tasks in the list.\n");
                break;
            }
            case EVENT: {
                String[] eventParts = Parser.parseEventCommand(userText);
                String description = eventParts[0];
                String from = eventParts[1];
                String to = eventParts[2];
                Task newTask = new Event(description, from, to);
                tasks.add(newTask);
                storage.save(tasks.getAll());
                output.append("Got it. I've added this task:\n");
                output.append("  " + newTask.toString() + "\n");
                output.append("Now you have " + tasks.size() + " tasks in the list.\n");
                break;
            }
            case DELETE: {
                int taskIndex = Parser.parseIndex(userText) - 1;
                if (taskIndex < 0 || taskIndex >= tasks.size()) {
                    output.append("OOPS!!! Task number does not exist.\n");
                    break;
                }
                Task deletedTask = tasks.remove(taskIndex);
                storage.save(tasks.getAll());
                output.append("Noted. I've removed this task:\n");
                output.append("  " + deletedTask.toString() + "\n");
                output.append("Now you have " + tasks.size() + " tasks in the list.\n");
                break;
            }
            default:
                output.append("Idk what that means! Use either todo deadline or event to create a task!\n");
            }
        } catch (Exception e) {
            output.append("OOPS!!! " + e.getMessage() + "\n");
        }
        return output.toString().trim();
    }

    public static void main(String[] args) {
        new Boof("./data/boof.txt").run();
    }

    /**
     * Returns the welcome message.
     */
    public String getWelcomeMessage() {
        return ui.showWelcome();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        return processCommand(input);
    }
}
