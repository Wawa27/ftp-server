package fil.coo.exceptions;

public class UnknownUserException extends FtpException {

    public UnknownUserException(String username) {
        super("502 User not known : " + username);
    }
}
