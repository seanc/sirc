package me.imsean.sirc;

import me.imsean.sirc.library.Bot;
import org.jibble.pircbot.IrcException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by sean on 1/24/16.
 */
public class Main {

    public Main(String server, String channel, int amount, String message) throws FileNotFoundException {
        final RandomAccessFile file = new RandomAccessFile("proxies.txt", "r");
        for(int i = 0; i < amount; i++) {
            String[] proxy = new String[0];
            try {
                final long loc = (long) (Math.random() * file.length());
                file.seek(loc);
                file.readLine();
                proxy = file.readLine().trim().split(":");
            } catch (IOException e) {
                e.printStackTrace();
            }

            final String[] finalProxy = proxy;
            Thread thread = new Thread() {
                public void run() {
                    try {
                        System.setProperty("socksProxyHost", finalProxy[0]);
                        System.setProperty("socksProxyPort", finalProxy[1]);

                        Bot bot = new Bot(message);
                        bot.setVerbose(true);
                        bot.connect(server);
                        bot.joinChannel(channel);
                        if(message != null) {
                            bot.sendMessage(channel, message);
                        }
                    } catch (IOException | IrcException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        }
    }

    public static void main(final String[] args) {
        if(args.length == 0) {
            System.out.println("Usage: java -jar sirc.jar [server] [channel] [amount] [message]");
            return;
        }
        try {
            if(args.length > 0) {
                String message;
                if(args.length < 3) {
                    message = null;
                } else {
                    String tmp = "";

                    for(int i = 3; i < args.length; i++) {
                        tmp += args[i] + " ";
                    }

                    message = tmp;
                }
                new Main(args[0], "#" + args[1], Integer.parseInt(args[2]), message);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
