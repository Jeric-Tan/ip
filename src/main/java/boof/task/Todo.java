package boof.task;

/**
 * Represents a todo which extends from the task class.
 */
public class Todo extends Task {

    /**
     * Constructor which creates a new todo.
     *
     * @param description the description of the todo
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T][" + getStatusIcon() + "] " + description;
    }
}
