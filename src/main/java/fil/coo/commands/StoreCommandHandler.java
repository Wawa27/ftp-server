package fil.coo.commands;

import fil.coo.abstracts.CommandHandler;
import fil.coo.exceptions.FtpException;
import fil.coo.v1.Channel;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class StoreCommandHandler extends CommandHandler {

    public StoreCommandHandler(Channel channel) {
        super("STOR", channel);
    }

    @Override
    protected String handle(String command) throws FtpException, IOException {
        String filename = command.split(" ")[1];

        this.channel.getCommandWriter().println("150 Ok to send data.");

        BufferedInputStream bufferedInputStream = new BufferedInputStream(
                this.channel.getDataSocket().getInputStream()
        );

        byte[] data = bufferedInputStream.readAllBytes();

        FileOutputStream fileOutputStream = new FileOutputStream(
                this.channel.getCurrentDirectory() + "/" + filename
        );

        fileOutputStream.write(data, 0, data.length);

        bufferedInputStream.close();
        fileOutputStream.close();

        return "226 Directory send OK.";
    }
}
