package boof.exception;

/**
 * A custom parent exception class for Boof application.
 */

public class BoofException extends Exception {
    public BoofException(String message) {
        super(message);
    }
}
