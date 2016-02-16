package me.imsean.sirc.library;

import org.jibble.pircbot.PircBot;

/**
 * Created by sean on 1/24/16.
 */
public class Bot extends PircBot {

    private String message;

    public Bot(String message) {
        this.setName(new RandomString(8 + (int)(Math.random() * ((15 - 8) + 1))).nextString());
        this.message = message;
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        if(message.contains("spam")) {
            if(this.message != null) {
                for(int i = 0; i < 15; i++) {
                    sendMessage(channel, this.message);
                }
            }
        }
    }

}
