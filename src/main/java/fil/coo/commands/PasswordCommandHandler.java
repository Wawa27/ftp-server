package fil.coo.commands;

import fil.coo.abstracts.CommandHandler;
import fil.coo.exceptions.IncorrectLoginException;
import fil.coo.v1.Channel;

public class PasswordCommandHandler extends CommandHandler {

    public PasswordCommandHandler(Channel channel) {
        super("PASS", channel);
    }

    @Override
    protected String handle(String command) throws IncorrectLoginException {
        String password = command.split(" ")[1];

        if (!"anonymous".equals(password) || !"anonymous".equals(channel.getUsername())) {
            throw new IncorrectLoginException();
        }

        return "230 Login successful.";
    }
}
