package fil.coo.abstracts;

import fil.coo.exceptions.CommandNotImplementedException;
import fil.coo.exceptions.FtpException;
import fil.coo.v1.Channel;

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

    public String process(String command) throws FtpException, IOException {
        String commandPrefix = command.split(" ")[0];
        if (Objects.equals(commandPrefix, this.commandPrefix)) {
            return this.handle(command);
        }
        if (this.nextHandler == null) {
            throw new CommandNotImplementedException("Command not implemented yet...");
        }
        return this.nextHandler.process(command);
    }

    protected abstract String handle(String command) throws FtpException, IOException;
}
