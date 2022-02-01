package fil.coo.commands;

import fil.coo.abstracts.CommandHandler;
import fil.coo.v1.Channel;

import java.io.IOException;

public class PrintWorkingDirectoryCommandHandler extends CommandHandler {

    public PrintWorkingDirectoryCommandHandler(Channel channel) {
        super("PWD", channel);
    }

    @Override
    protected void handle(String command) throws IOException {
        this.channel.getCommandWriter().println("230 " + this.channel.getCurrentDirectory().getCanonicalPath());
    }
}
