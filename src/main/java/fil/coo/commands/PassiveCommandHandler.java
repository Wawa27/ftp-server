package fil.coo.commands;

import fil.coo.abstracts.CommandHandler;
import fil.coo.v1.Channel;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

public class PassiveCommandHandler extends CommandHandler {

    public PassiveCommandHandler(Channel channel) {
        super("PASV", channel);
    }

    @Override
    protected String handle(String command) throws IOException {
        ServerSocket dataServerSocket = new ServerSocket(0);
        int portFirstPart = dataServerSocket.getLocalPort() / 256;
        int portSecondPart = dataServerSocket.getLocalPort() % 256;
        String address = Inet4Address.getLocalHost().getHostAddress().replaceAll("\\.", ",");
        this.channel.getCommandWriter().println("227 Entering Passive Mode (" + address + "," + portFirstPart + "," + portSecondPart + ")");

        Socket dataSocket = dataServerSocket.accept();
        this.channel.setDataSocket(dataSocket);

        // We need to return before opening the socket, after that, nothing needs to be returned
        return null;
    }
}
