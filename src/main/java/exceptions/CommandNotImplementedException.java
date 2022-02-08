package exceptions;

/**
 * Thrown by the chain of responsibility pattern when no handler can handle the request.
 */
public class CommandNotImplementedException extends FtpException {

    public CommandNotImplementedException(String message) {
        super(message);
    }
}
