package com.zs.application;
import java.util.ArrayList;

public class MessageDatabase { 
    private ArrayList<Message> messages;

    public MessageDatabase() {
        messages = new ArrayList<>();
    }

    public void sendMessage(Message message) {
        messages.add(message);
    }

    public ArrayList<Message> getMessageForUser(String username) {
        ArrayList<Message> userMessages = new ArrayList<>();

        for (Message message: messages) {
            if (message.getReceiver().equals(username)) {
                userMessages.add(message);
            }
        }
        return userMessages;
    }

    public void deleteMessage(Message message) {
        messages.remove(message);
    }
}
