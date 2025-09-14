package boof.command;

import boof.exception.BoofException;
import boof.storage.Storage;
import boof.task.TaskList;
import boof.ui.Ui;

/**
 * Represents a parent user command with its action and parameters.
 */
public abstract class Command {
    /**
     * Executes the command and returns the result as a String.
     * @param storage the storage instance
     * @param tasks the task list instance
     * @param ui the UI instance
     * @return the result of executing the command
     * @throws BoofException if an error occurs during command execution
     */
    public abstract String execute(Storage storage, TaskList tasks, Ui ui) throws BoofException;
}
