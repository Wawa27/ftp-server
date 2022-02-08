package exceptions;

/**
 * This exception should be thrown when a parameter is recognized but the handler is unable to handle it.
 */
public class ParameterNotSupportedException extends FtpException {

    public ParameterNotSupportedException(String message) {
        super(message);
    }
}
