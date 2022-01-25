package abstracts;

import exceptions.CommandNotImplementedException;
import v1.Channel;

import java.io.IOException;
import java.util.Objects;

public abstract class CommandHandler {
    protected Channel channel;
    protected String commandPrefix;
    protected CommandHandler nextHandler;

    public CommandHandler(String commandPrefix, Channel channel) {
        this.channel = channel;
        this.commandPrefix = commandPrefix;
    }

    public void setNext(CommandHandler commandHandler) {
        if (this.nextHandler == null) {
            this.nextHandler = commandHandler;
        } else {
            this.nextHandler.setNext(commandHandler);
        }
    }

    public void process(String command) throws CommandNotImplementedException, IOException {
        String commandPrefix = command.split(" ")[0];
        if (Objects.equals(commandPrefix, this.commandPrefix)) {
            this.handle(command);
        } else {
            if (this.nextHandler == null) {
                throw new CommandNotImplementedException("Command not implemented yet...");
            }
            this.nextHandler.process(command);
        }
    }

    protected abstract void handle(String command) throws IOException, CommandNotImplementedException;
}
