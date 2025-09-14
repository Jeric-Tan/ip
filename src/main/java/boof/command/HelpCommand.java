package boof.command;

import boof.storage.Storage;
import boof.task.TaskList;
import boof.ui.Ui;

/**
 * Represents a command to display help information.
 */
public class HelpCommand extends Command {

    @Override
    public String execute(Storage storage, TaskList tasks, Ui ui) {
        return ui.showHelp();
    }
}


