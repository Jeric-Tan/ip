package boof.command;

import boof.storage.Storage;
import boof.task.TaskList;
import boof.ui.Ui;

/**
 * Represents a command that is not recognized.
 */
public class UnknownCommand extends Command {

    private final String input;

    public UnknownCommand(String input) {
        this.input = input;
    }

    @Override
    public String execute(Storage storage, TaskList tasks, Ui ui) {
        String message = "I don't know what that means: \"" + input + "\"\n"
                + "Please use ONLY todo, deadline, event, list, mark, unmark, delete, find, or bye.";
        return ui.displayMessageWithDivider(message);
    }
}
