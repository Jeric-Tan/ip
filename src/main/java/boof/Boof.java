package boof;

import boof.parser.Parser;
import boof.storage.Storage;
import boof.task.Deadline;
import boof.task.Event;
import boof.task.Task;
import boof.task.TaskList;
import boof.task.Todo;
import boof.ui.Ui;

public class Boof {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

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

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            String userText = ui.readCommand();
            Parser.CommandType commandType = Parser.getCommandType(userText);
            try {
                switch (commandType) {
                    case BYE:
                        isExit = true;
                        ui.showExit();
                        break;
                    case LIST:
                        ui.showMessage("    ------- Your List -------");
                        for (int i = 0; i < tasks.size(); i++) {
                            ui.showMessage("     " + (i + 1) + "." + tasks.get(i).toString());
                        }
                        ui.displayLine();
                        break;
                    case MARK: {
                        int taskIndex = Parser.parseIndex(userText) - 1;
                        Task t = tasks.get(taskIndex);
                        t.markAsDone();
                        storage.save(tasks.getAll());
                        ui.displayLine();
                        ui.showMessage("      Nice! I've marked this task as done:");
                        ui.showMessage("        " + t.toString());
                        ui.displayLine();
                        break;
                    }
                    case UNMARK: {
                        int taskIndex = Parser.parseIndex(userText) - 1;
                        Task t = tasks.get(taskIndex);
                        t.unmarkAsDone();
                        storage.save(tasks.getAll());
                        ui.displayLine();
                        ui.showMessage("      OK, I've marked this task as not done yet:");
                        ui.showMessage("        " + t.toString());
                        ui.displayLine();
                        break;
                    }
                    case TODO: {
                        String description = Parser.parseTodoDescription(userText);
                        Task newTask = new Todo(description);
                        tasks.add(newTask);
                        storage.save(tasks.getAll());
                        ui.displayLine();
                        ui.showMessage("      Got it. I've added this task:");
                        ui.showMessage("        " + newTask.toString());
                        ui.showMessage("      Now you have " + tasks.size() + " tasks in the list.");
                        ui.displayLine();
                        break;
                    }
                    case DEADLINE: {
                        String[] deadlineParts = Parser.parseDeadlineCommand(userText);
                        String description = deadlineParts[0];
                        String byDate = deadlineParts[1];
                        Task newTask = new Deadline(description, byDate);
                        tasks.add(newTask);
                        storage.save(tasks.getAll());
                        ui.displayLine();
                        ui.showMessage("      Got it. I've added this task:");
                        ui.showMessage("        " + newTask.toString());
                        ui.showMessage("      Now you have " + tasks.size() + " tasks in the list.");
                        ui.displayLine();
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
                        ui.displayLine();
                        ui.showMessage("      Got it. I've added this task:");
                        ui.showMessage("        " + newTask.toString());
                        ui.showMessage("      Now you have " + tasks.size() + " tasks in the list.");
                        ui.displayLine();
                        break;
                    }
                    case DELETE: {
                        int taskIndex = Parser.parseIndex(userText) - 1;
                        if (taskIndex < 0 || taskIndex >= tasks.size()) {
                            ui.displayLine();
                            ui.showMessage("      OOPS!!! Task number does not exist.");
                            ui.displayLine();
                            break;
                        }
                        Task deletedTask = tasks.remove(taskIndex);
                        storage.save(tasks.getAll());
                        ui.displayLine();
                        ui.showMessage("      Noted. I've removed this task:");
                        ui.showMessage("        " + deletedTask.toString());
                        ui.showMessage("      Now you have " + tasks.size() + " tasks in the list.");
                        ui.displayLine();
                        break;
                    }
                    default:
                        ui.displayLine();
                        ui.showMessage(
                                "        Idk what that means! Use either todo deadline or event to create a task!");
                        ui.displayLine();
                }
            } catch (Exception e) {
                ui.displayLine();
                ui.showMessage("      OOPS!!! " + e.getMessage());
                ui.displayLine();
            }
        }
    }

    public static void main(String[] args) {
        new Boof("./data/boof.txt").run();
    }
}
