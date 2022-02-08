package commands;

import abstracts.CommandHandler;
import v1.Channel;

import java.io.File;

/**
 * The CWD command is issued to change the client's current working directory to the path specified with the command.
 */
public class ChangeWorkingDirectoryCommandHandler extends CommandHandler {

    public ChangeWorkingDirectoryCommandHandler(Channel channel) {
        super("CWD", channel);
    }

    @Override
    protected void handle(String command) {
        this.channel.setCurrentDirectory(
                new File(this.channel.getCurrentDirectory().getAbsolutePath() + "/" + command.split(" ")[1])
        );
        this.channel.getCommandWriter().println("200 OK");
    }
}
