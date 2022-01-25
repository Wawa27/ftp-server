package commands;

import abstracts.CommandHandler;
import v1.Channel;

public class UserCommandHandler extends CommandHandler {

    public UserCommandHandler(Channel channel) {
        super("USER", channel);
    }

    @Override
    protected void handle(String command) {
        this.channel.setUsername(command.split(" ")[1]);
        this.channel.getCommandWriter().println("230 OK");
    }
}
