package commands;

import abstracts.CommandHandler;
import exceptions.CommandNotImplementedException;
import v1.Channel;

/**
 * This command handler changes the type of data used for transfers.
 */
public class TypeCommandHandler extends CommandHandler {

    public TypeCommandHandler(Channel channel) {
        super("TYPE", channel);
    }

    @Override
    protected void handle(String command) throws CommandNotImplementedException {
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
