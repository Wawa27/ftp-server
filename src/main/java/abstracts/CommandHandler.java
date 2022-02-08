package abstracts;

import exceptions.CommandNotImplementedException;
import v1.Channel;

import java.io.IOException;
import java.util.Objects;

/**
 * An abstract class that receive request and dispatches them to the next handler if it cannot handle it.
 */
public abstract class CommandHandler {
    protected Channel channel;
    protected String commandPrefix;
    protected CommandHandler nextHandler;

    /**
     * Creates a new handler
     * @param commandPrefix The prefix used to find if the handler can handle the request
     * @param channel The user's channel
     */
    public CommandHandler(String commandPrefix, Channel channel) {
        this.channel = channel;
        this.commandPrefix = commandPrefix;
    }

    /**
     * Add a handler to this chain of handlers
     * @param commandHandler The handler to add to the chain
     */
    public void setNext(CommandHandler commandHandler) {
        if (this.nextHandler == null) {
            this.nextHandler = commandHandler;
        } else {
            this.nextHandler.setNext(commandHandler);
        }
    }

    /**
     * A method that checks if this handler can handle the request thanks to the prefix specified by the concrete
     * implementation.
     * @param command The full ftp command
     * @throws CommandNotImplementedException Thrown if all the handlers cannot handle the request
     * @throws IOException Thrown by concrete implementations in the handle method
     */
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
