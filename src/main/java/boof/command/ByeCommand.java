package boof.command;

import boof.storage.Storage;
import boof.task.TaskList;
import boof.ui.Ui;

/**
 * Represents a command to exit the program.
 */
public class ByeCommand extends Command {
    @Override
    public String execute(Storage storage, TaskList tasks, Ui ui) {
        ui.showExit();
        return "exit";
    }
}

