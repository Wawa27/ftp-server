package fil.coo.exceptions;

public class IncorrectLoginException extends FtpException {

    public IncorrectLoginException() {
        super("530 Incorrect login");
    }
}
