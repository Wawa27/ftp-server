package fil.coo.commands;

import fil.coo.abstracts.CommandHandler;
import fil.coo.exceptions.CommandNotImplementedException;
import fil.coo.v1.Channel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SizeCommandHandler extends CommandHandler {

    public SizeCommandHandler(Channel channel) {
        super("SIZE", channel);
    }

    @Override
    protected String handle(String command) throws IOException, CommandNotImplementedException {
        Path path = Paths.get(command.split(" ")[1]);
        return "213 " + Files.size(path);
    }
}
