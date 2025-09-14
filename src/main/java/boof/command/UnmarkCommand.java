package boof.command;

import boof.storage.Storage;
import boof.task.TaskList;
import boof.ui.Ui;

/**
 * Represents a command to unmark a task as not done.
 */
public class UnmarkCommand extends Command {
    private final int taskIndex;

    /**
     * Constructor for UnmarkCommand.
     *
     * @param taskIndex The index of the task to be unmarked.
     */
    public UnmarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public String execute(Storage storage, TaskList tasks, Ui ui) {
        if (taskIndex < 0 || taskIndex >= tasks.getTaskListSize()) {
            return ui.displayMessageWithDivider("Invalid task index.");
        }
        tasks.unmarkTask(taskIndex);
        storage.saveTasks(tasks.getAllTasks());
        return ui.displayMessageWithDivider("I've unmarked this task:\n        " + tasks.getTask(taskIndex));
    }

}
