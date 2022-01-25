package commands;

import abstracts.CommandHandler;
import exceptions.CommandNotImplementedException;
import v1.Channel;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class RetrieveCommandHandler extends CommandHandler {

    public RetrieveCommandHandler(Channel channel) {
        super("RETR", channel);
    }

    @Override
    protected void handle(String command) throws IOException, CommandNotImplementedException {
        File file = new File(command.split(" ")[1]);
        FileInputStream fileInputStream = new FileInputStream(file);

        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(this.channel.getDataSocket().getOutputStream());

        byte[] bytes = fileInputStream.readAllBytes();
        this.channel.getCommandWriter().println("150 Opening BINARY mode");

        bufferedOutputStream.write(bytes);

        this.channel.getCommandWriter().println("226 Transfer complete.");
        bufferedOutputStream.close();
    }
}

