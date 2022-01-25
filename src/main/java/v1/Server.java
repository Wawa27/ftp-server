package v1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private String dataFolderPath;
    private int port;

    public Server(int port, String dataFolderPath) {
        this.dataFolderPath = dataFolderPath;
        this.port = port;
    }

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

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        String dataFolderPath = args[1];
        Server server = new Server(port, dataFolderPath);
        server.listen();
    }
}
