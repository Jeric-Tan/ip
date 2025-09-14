package boof.command;

import boof.storage.Storage;
import boof.task.TaskList;
import boof.ui.Ui;

/**
 * Represents a command to mark a task as done.
 */
public class MarkCommand extends Command {
    private final int taskIndex;

    /**
     * Constructor for MarkCommand.
     *
     * @param taskIndex The index of the task to be marked.
     */
    public MarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public String execute(Storage storage, TaskList tasks, Ui ui) {
        if (taskIndex < 0 || taskIndex >= tasks.getTaskListSize()) {
            return ui.displayMessageWithDivider("Invalid task index.");
        }
        tasks.markTask(taskIndex);
        storage.saveTasks(tasks.getAllTasks());
        return ui.displayMessageWithDivider("I've marked this task as done:\n        " + tasks.getTask(taskIndex));
    }
}
