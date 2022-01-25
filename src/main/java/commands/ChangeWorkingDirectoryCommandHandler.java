package commands;

import abstracts.CommandHandler;
import exceptions.CommandNotImplementedException;
import v1.Channel;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ChangeWorkingDirectoryCommandHandler extends CommandHandler {

    public ChangeWorkingDirectoryCommandHandler(Channel channel) {
        super("CWD", channel);
    }

    @Override
    protected void handle(String command) throws IOException, CommandNotImplementedException {
        this.channel.setCurrentDirectory(new File(this.channel.getCurrentDirectory().getAbsolutePath() + "/" + command.split(" ")[1]));
        this.channel.getCommandWriter().println("200 OK");
    }
}
