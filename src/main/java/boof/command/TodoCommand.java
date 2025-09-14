package boof.command;

import boof.storage.Storage;
import boof.task.Task;
import boof.task.TaskList;
import boof.task.Todo;
import boof.ui.Ui;

/**
 * Represents a command to add a todo task.
 */
public class TodoCommand extends Command {
    private final String description;

    /**
     * Constructor for TodoCommand.
     *
     * @param description The description of the todo task.
     */
    public TodoCommand(String description) {
        this.description = description;
    }

    @Override
    public String execute(Storage storage, TaskList tasks, Ui ui) {
        Task newTask = new Todo(description);
        tasks.addTask(newTask);
        storage.saveTasks(tasks.getAllTasks());
        String s = "      Got it. I've added this task:\n        "
            + newTask + "\n      Now you have " + tasks.getTaskListSize() + " tasks in the list.";
        return ui.displayMessageWithDivider(s);
    }

}
