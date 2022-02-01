package fil.coo.commands;

import fil.coo.abstracts.CommandHandler;
import fil.coo.exceptions.FtpException;
import fil.coo.v1.Channel;

public class UserCommandHandler extends CommandHandler {

    public UserCommandHandler(Channel channel) {
        super("USER", channel);
    }

    @Override
    protected void handle(String command) throws FtpException {
        String username = command.split(" ")[1];

        this.channel.setUsername(username);
        this.channel.getCommandWriter().println("331 Please specify the password");
    }
}
