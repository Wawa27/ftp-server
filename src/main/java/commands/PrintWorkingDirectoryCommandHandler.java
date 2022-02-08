package commands;

import abstracts.CommandHandler;
import v1.Channel;

import java.io.IOException;

/**
 * This command handler sends the current working directory to the user.
 */
public class PrintWorkingDirectoryCommandHandler extends CommandHandler {

    public PrintWorkingDirectoryCommandHandler(Channel channel) {
        super("PWD", channel);
    }

    @Override
    protected void handle(String command) throws IOException {
        this.channel.getCommandWriter().println("230 " + this.channel.getCurrentDirectory().getCanonicalPath());
    }
}
