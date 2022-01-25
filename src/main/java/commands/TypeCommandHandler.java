package commands;

import abstracts.CommandHandler;
import exceptions.CommandNotImplementedException;
import v1.Channel;

import java.io.IOException;

public class TypeCommandHandler extends CommandHandler {

    public TypeCommandHandler(Channel channel) {
        super("TYPE", channel);
    }

    @Override
    protected void handle(String command) throws IOException, CommandNotImplementedException {
        char type = command.split(" ")[1].charAt(0);
        switch (type) {
            case 'A', 'I' -> {
                this.channel.setCurrentType(type);
                this.channel.getCommandWriter().println("200 OK");
            }
            default -> throw new CommandNotImplementedException("The type " + type + " is not supported.");
        }
    }
}
