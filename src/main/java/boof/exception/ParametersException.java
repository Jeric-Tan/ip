package boof.exception;

/**
 * An exception thats thrown when there is an issue with the parameters provided by the user.
 */
public class ParametersException extends BoofException {
    public ParametersException(String message) {
        super("There is an issue with the parameters you have provided: " + message);
    }
}
