package boof.command;

import boof.storage.Storage;
import boof.task.Event;
import boof.task.Task;
import boof.task.TaskList;
import boof.ui.Ui;

/**
 * Represents a command to create an Event
 */
public class EventCommand extends Command {
    private final String description;
    private final String from;
    private final String to;

    /**
     * Constructor for EventCommand.
     *
     * @param description The description of the event task.
     * @param at          The time of the event task.
     */
    public EventCommand(String description, String from, String to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    @Override
    public String execute(Storage storage, TaskList tasks, Ui ui) {
        Task event = new Event(description, from, to);
        tasks.addTask(event);
        storage.saveTasks(tasks.getAllTasks());
        String s = "      Got it. I've added this task:\n        "
            + event + "\n      Now you have " + tasks.getTaskListSize() + " tasks in the list.";
        return ui.displayMessageWithDivider(s);
    }
}
