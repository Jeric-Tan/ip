package boof.task;

/**
 * Represents a todo which extends from the task class.
 */
public class Todo extends Task {
  
  public Todo(String description) {
    super(description);
  }

  @Override
  public String toString() {
    return "[T][" + getStatusIcon() + "] " + description;
  }
}