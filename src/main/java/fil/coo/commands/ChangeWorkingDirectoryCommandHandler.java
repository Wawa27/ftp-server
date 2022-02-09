package fil.coo.commands;

import fil.coo.abstracts.CommandHandler;
import fil.coo.exceptions.CommandNotImplementedException;
import fil.coo.v1.Channel;

import java.io.File;
import java.io.IOException;

public class ChangeWorkingDirectoryCommandHandler extends CommandHandler {

    public ChangeWorkingDirectoryCommandHandler(Channel channel) {
        super("CWD", channel);
    }

    @Override
    protected String handle(String command) throws IOException, CommandNotImplementedException {
        String path = command.split(" ")[1];

        if (path.startsWith("/")) {
            // Absolute path
            this.channel.setCurrentDirectory(new File(path));
        } else {
            // Relative path
            this.channel.setCurrentDirectory(
                    new File(this.channel.getCurrentDirectory().getAbsolutePath() + "/" + path)
            );
        }

        return "200 OK";
    }
}
