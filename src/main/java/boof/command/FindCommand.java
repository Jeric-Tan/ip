package boof.command;

import boof.storage.Storage;
import boof.task.TaskList;
import boof.ui.Ui;

/**
 * Represents a command to find tasks containing a specific keyword.
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Constructor for FindCommand.
     *
     * @param keyword The keyword to search for in task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String execute(Storage storage, TaskList tasks, Ui ui) {
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        int count = 0;
        for (int i = 0; i < tasks.getTaskListSize(); i++) {
            if (tasks.getTask(i).getDescription().contains(keyword)) {
                count++;
                sb.append("  ").append(count).append(". ").append(tasks.getTask(i)).append("\n");
            }
        }
        if (count == 0) {
            return ui.displayMessageWithDivider("No matching tasks found.");
        }
        return ui.displayMessageWithDivider(sb.toString().trim());
    }
}
