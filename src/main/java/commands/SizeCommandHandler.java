package commands;

import abstracts.CommandHandler;
import v1.Channel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SizeCommandHandler extends CommandHandler {

    public SizeCommandHandler(Channel channel) {
        super("SIZE", channel);
    }

    @Override
    protected void handle(String command) throws IOException {
        Path path = Paths.get(command.split(" ")[1]);
        this.channel.getCommandWriter().println("213 " + Files.size(path));
    }
}
