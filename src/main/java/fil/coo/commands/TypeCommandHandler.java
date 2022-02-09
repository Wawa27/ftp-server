package fil.coo.commands;

import fil.coo.abstracts.CommandHandler;
import fil.coo.exceptions.CommandNotImplementedException;
import fil.coo.v1.Channel;

import java.io.IOException;

public class TypeCommandHandler extends CommandHandler {

    public TypeCommandHandler(Channel channel) {
        super("TYPE", channel);
    }

    @Override
    protected String handle(String command) throws IOException, CommandNotImplementedException {
        char type = command.split(" ")[1].charAt(0);
        switch (type) {
            case 'A', 'I' -> {
                this.channel.setCurrentType(type);
                return "200 OK";
            }
            default -> throw new CommandNotImplementedException("The type " + type + " is not supported.");
        }
    }
}
