package fil.coo.v1;

import fil.coo.abstracts.CommandHandler;
import fil.coo.commands.*;
import fil.coo.exceptions.FtpException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Channel extends Thread {
    private CommandHandler[] commandHandlers;
    private String username;
    private File currentDirectory;
    private char currentType;
    private String dataFolderPath;

    /**
     * Command socket
     */
    private final Socket commandSocket;
    private final BufferedReader commandReader;
    private final PrintWriter commandWriter;

    /**
     * Data socket
     */
    private Socket dataSocket;

    public Channel(Socket socket, String dataFolderPath) throws IOException {
        this.commandSocket = socket;
        this.commandReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.commandWriter = new PrintWriter(socket.getOutputStream(), true);
        this.setupCommandHandlers();
        this.commandWriter.println("220 (vsFTPd 3.0.3)");
        this.currentType = 'A';
        this.dataFolderPath = dataFolderPath;
    }

    /**
     * Create a chain of command handlers
     */
    private void setupCommandHandlers() {
        this.commandHandlers = new CommandHandler[]{
                new PasswordCommandHandler(this),
                new StoreCommandHandler(this),
                new ListDirectoryCommandHandler(this),
                new UserCommandHandler(this),
                new PassiveCommandHandler(this),
                new PrintWorkingDirectoryCommandHandler(this),
                new TypeCommandHandler(this),
                new RetrieveCommandHandler(this),
                new SizeCommandHandler(this),
                new ChangeWorkingDirectoryCommandHandler(this)
        };
        CommandHandler[] handlers = this.commandHandlers;
        for (int i = 1; i < handlers.length; i++) {
            CommandHandler commandHandler = handlers[i];
            commandHandlers[0].setNext(commandHandler);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String command = this.commandReader.readLine();
                if (command != null) {
                    System.out.println("Command from " + commandSocket.getRemoteSocketAddress() + " : " + command);
                    String response = this.commandHandlers[0].process(command);
                    if (response != null) {
                        this.sendMessage(response);
                    }
                }
            } catch (IOException e) {
                this.commandWriter.println("500 Unknown error");
            } catch (FtpException e) {
                this.commandWriter.println(e.getMessage());
            }
        }
    }

    public void sendMessage(String message) {
        this.commandWriter.println(message);
    }

    public Socket getDataSocket() {
        return dataSocket;
    }

    public void setDataSocket(Socket dataSocket) {
        this.dataSocket = dataSocket;
    }

    public File getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(File currentDirectory) {
        this.currentDirectory = currentDirectory;
    }

    public PrintWriter getCommandWriter() {
        return commandWriter;
    }

    public String getUsername() {
        return username;
    }

    public void setCurrentType(char currentType) {
        this.currentType = currentType;
    }

    public char getCurrentType() {
        return this.currentType;
    }

    public void setUsername(String username) {
        this.currentDirectory = new File(this.dataFolderPath + "/" + username + "/");
        this.username = username;
    }
}
