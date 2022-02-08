package commands;

import abstracts.CommandHandler;
import v1.Channel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This command handler makes the server enter a passive server.
 * It chooses a random not-used port and the specified host and send them to the client.
 */
public class PassiveCommandHandler extends CommandHandler {

    public PassiveCommandHandler(Channel channel) {
        super("PASV", channel);
    }

    @Override
    protected void handle(String command) throws IOException {
        ServerSocket dataServerSocket = new ServerSocket(0);
        int portFirstPart = dataServerSocket.getLocalPort() / 256;
        int portSecondPart = dataServerSocket.getLocalPort() % 256;
        this.channel.getCommandWriter().println("227 Entering Passive Mode (172,18,13,134," + portFirstPart + "," + portSecondPart + ")");
        Socket dataSocket = dataServerSocket.accept();
        this.channel.setDataSocket(dataSocket);
    }
}
