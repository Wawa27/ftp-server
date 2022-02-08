package v1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A ftp server, clients are handled asynchronously by their channel.
 */
public class Server {
    private ServerSocket serverSocket;
    private String dataFolderPath;
    private int port;

    public Server(int port, String dataFolderPath) {
        this.dataFolderPath = dataFolderPath;
        this.port = port;
    }

    /**
     * Start listening for clients
     * @throws IOException A socket exception
     */
    public void listen() throws IOException {
        this.serverSocket = new ServerSocket(port);

        System.out.println("Starting listening on port " + this.serverSocket.getLocalPort() + " ...");
        while (true) {
            Socket socket = this.serverSocket.accept();
            System.out.println("New client connected !");
            Channel channel = new Channel(socket, dataFolderPath);
            channel.start();
        }
    }

    /**
     * Start the server using the specified args
     * @param args An array of string containing, the port (0-65535), default 0, and the folder where files are deleted/uploaded
     *             from, default the working directory.
     * @throws IOException A socket exception
     */
    public static void main(String[] args) throws IOException {
        int port = args[0] != null ? Integer.parseInt(args[0]) : 0;
        String dataFolderPath = args[1];
        Server server = new Server(port, dataFolderPath);
        server.listen();
    }
}
