package boof.exception;

/**
 * An exception thats thrown when the user inputs an invalid command.
 */
public class InvalidCommandException extends BoofException {
    public InvalidCommandException(String message) {
        super("You have entered an invalid command: " + message);
    }
}
