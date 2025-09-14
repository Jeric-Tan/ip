package boof.command;

import boof.storage.Storage;
import boof.task.TaskList;
import boof.ui.Ui;

/**
 * Represents a command to delete a task.
 */
public class DeleteCommand extends Command {
    private final int taskIndex;

    /**
     * Constructor for DeleteCommand.
     *
     * @param taskIndex The index of the task to be deleted.
     */
    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public String execute(Storage storage, TaskList tasks, Ui ui) {
        if (taskIndex < 0 || taskIndex >= tasks.getTaskListSize()) {
            return ui.displayMessageWithDivider("Invalid task index.");
        }
        String s = "      Noted. I've removed this task:\n        "
            + tasks.getTask(taskIndex) + "\n      Now you have " + (tasks.getTaskListSize() - 1)
            + " tasks in the list.";
        tasks.removeTask(taskIndex);
        storage.saveTasks(tasks.getAllTasks());
        return ui.displayMessageWithDivider(s);
    }
}

