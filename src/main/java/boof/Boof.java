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
            String[] parts = userText.split(" ");
            String command = Parser.getCommand(userText);

            switch (command) {
                case "bye":
                    isExit = true;
                    ui.showExit();
                    break;
                case "list":
                    ui.showMessage("    ------- Your List -------");
                    for (int i = 0; i < tasks.size(); i++) {
                        ui.showMessage("     " + (i + 1) + "." + tasks.get(i).toString());
                    }
                    ui.displayLine();
                    break;
                case "mark": {
                    int taskIndex = Integer.parseInt(parts[1]) - 1;
                    Task t = tasks.get(taskIndex);
                    t.markAsDone();
                    storage.save(tasks.getAll());
                    ui.displayLine();
                    ui.showMessage("      Nice! I've marked this task as done:");
                    ui.showMessage("        " + t.toString());
                    ui.displayLine();
                    break;
                }
                case "unmark": {
                    int taskIndex = Integer.parseInt(parts[1]) - 1;
                    Task t = tasks.get(taskIndex);
                    t.unmarkAsDone();
                    storage.save(tasks.getAll());
                    ui.displayLine();
                    ui.showMessage("      OK, I've marked this task as not done yet:");
                    ui.showMessage("        " + t.toString());
                    ui.displayLine();
                    break;
                }
                case "todo": {
                    if (parts.length < 2) {
                        ui.displayLine();
                        ui.showMessage("      OOPS!!! The description of a todo cannot be empty.");
                        ui.displayLine();
                        break;
                    }
                    String description = userText.substring(5);
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
                case "deadline": {
                    if (parts.length < 2) {
                        ui.displayLine();
                        ui.showMessage("      OOPS!!! The description of a deadline cannot be empty.");
                        ui.displayLine();
                        break;
                    }
                    String[] deadlineParts = userText.split(" /by ", 2);
                    if (deadlineParts.length < 2) {
                        ui.displayLine();
                        ui.showMessage(
                                "      OOPS!!! Please specify a /by date in the correct format (e.g., 2019-10-15 1800).");
                        ui.displayLine();
                        break;
                    }
                    String description = deadlineParts[0].substring(9);
                    String byDate = deadlineParts[1];
                    try {
                        Task newTask = new Deadline(description, byDate);
                        tasks.add(newTask);
                        storage.save(tasks.getAll());
                        ui.displayLine();
                        ui.showMessage("      Got it. I've added this task:");
                        ui.showMessage("        " + newTask.toString());
                        ui.showMessage("      Now you have " + tasks.size() + " tasks in the list.");
                        ui.displayLine();
                    } catch (Exception e) {
                        ui.displayLine();
                        ui.showMessage(
                                "      OOPS!!! Invalid date format. Please use yyyy-MM-dd HHmm or d/M/yyyy HHmm.");
                        ui.displayLine();
                    }
                    break;
                }
                case "event": {
                    if (parts.length < 2) {
                        ui.displayLine();
                        ui.showMessage("      OOPS!!! The description of an event cannot be empty.");
                        ui.displayLine();
                        break;
                    }
                    String[] eventSplit = userText.split(" /from ");
                    String description = eventSplit[0].substring(6);
                    String[] fromTo = eventSplit[1].split(" /to ");
                    String from = fromTo[0];
                    String to = fromTo.length > 1 ? fromTo[1] : "";
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
                case "delete": {
                    if (parts.length < 2) {
                        ui.displayLine();
                        ui.showMessage("      OOPS!!! Specify the task number to delete.");
                        ui.displayLine();
                        break;
                    }
                    int taskIndex = Integer.parseInt(parts[1]) - 1;
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
                    ui.showMessage("        Idk what that means! Use either todo deadline or event to create a task!");
                    ui.displayLine();
            }
        }
    }

    public static void main(String[] args) {
        new Boof("./data/boof.txt").run();
    }
}
