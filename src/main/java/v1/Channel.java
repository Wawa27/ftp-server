package v1;

import abstracts.CommandHandler;
import commands.*;
import exceptions.CommandNotImplementedException;

import java.io.*;
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
                    this.commandHandlers[0].process(command);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CommandNotImplementedException e) {
                this.commandWriter.println("502 Command not implemented.");
            }
        }
    }

    public Socket getDataSocket() {
        return dataSocket;
    }

    public void setDataSocket(Socket dataSocket) throws IOException {
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

    public void setUsername(String username) {
        this.currentDirectory = new File(this.dataFolderPath + "/" + username + "/");
        this.username = username;
    }
}
