package boof.command;

import boof.storage.Storage;
import boof.task.TaskList;
import boof.ui.Ui;

/**
 * Represents a command to list all tasks.
 */
public class ListCommand extends Command {

    @Override
    public String execute(Storage storage, TaskList tasks, Ui ui) {
        if (tasks.getTaskListSize() == 0) {
            return ui.displayMessageWithDivider("Your task list is empty.");
        }
        StringBuilder sb = new StringBuilder("    ------- Your List ------- \n");
        for (int i = 0; i < tasks.getTaskListSize(); i++) {
            sb.append("  ").append(i + 1).append(". ").append(tasks.getTask(i)).append("\n");
        }
        return ui.displayMessageWithDivider(sb.toString());
    }
}
