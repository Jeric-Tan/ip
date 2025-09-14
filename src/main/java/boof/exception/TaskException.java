package boof.exception;

/**
 * An exception thats thrown when there is an issue with a task.
 */
public class TaskException extends BoofException {
    public TaskException(String message) {
        super("Tasks does not exist, please check the specified number: " + message);
    }
}
