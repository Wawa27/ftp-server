package exceptions;

public class CommandNotImplementedException extends FtpException {

    public CommandNotImplementedException(String message) {
        super(message);
    }
}
