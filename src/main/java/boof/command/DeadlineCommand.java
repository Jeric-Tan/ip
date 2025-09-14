package boof.command;

import boof.storage.Storage;
import boof.task.Deadline;
import boof.task.Task;
import boof.task.TaskList;
import boof.ui.Ui;

/**
 * Represents a command to add a deadline task.
 */
public class DeadlineCommand extends Command {
    private final String description;
    private final String byDate;

    /**
     * Constructor for DeadlineCommand.
     *
     * @param description The description of the deadline task.
     * @param byDate      The due date of the deadline task.
     */
    public DeadlineCommand(String description, String byDate) {
        this.description = description;
        this.byDate = byDate;
    }

    @Override
    public String execute(Storage storage, TaskList tasks, Ui ui) {
        Task deadline = new Deadline(description, byDate);
        tasks.addTask(deadline);
        storage.saveTasks(tasks.getAllTasks());
        String s = "      Got it. I've added this task:\n        "
            + deadline + "\n      Now you have " + tasks.getTaskListSize() + " tasks in the list.";
        return ui.displayMessageWithDivider(s);
    }
}
