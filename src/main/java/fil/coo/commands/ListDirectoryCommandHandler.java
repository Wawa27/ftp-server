package fil.coo.commands;

import fil.coo.abstracts.CommandHandler;
import fil.coo.v1.Channel;

import java.io.*;

public class ListDirectoryCommandHandler extends CommandHandler {

    public ListDirectoryCommandHandler(Channel channel) {
        super("LIST", channel);
    }

    @Override
    protected String handle(String command) throws IOException {
        this.channel.getCommandWriter().println("150 Here comes the directory listing.");

        PrintWriter printWriter = new PrintWriter(this.channel.getDataSocket().getOutputStream(), true);

        System.out.println(this.channel.getCurrentDirectory());
        System.out.println(this.channel.getCurrentDirectory().getAbsolutePath());
        for (File f : this.channel.getCurrentDirectory().listFiles()) {
            String stringBuilder = (f.isDirectory() ? "d" : "-") +
                    (f.canRead() ? "r" : "-") +
                    (f.canWrite() ? "w" : "-") +
                    (f.canExecute() ? "x" : "-") +
                    "------" +
                    "    " +
                    "1" +
                    " " +
                    "11081" +
                    "    " +
                    "1005" +
                    "            " +
                    "4096 " +
                    "Jan 04 16:55 " +
                    f.getName();
            printWriter.println(stringBuilder);
        }
        printWriter.close();
        return "226 Directory send OK.";
    }
}
