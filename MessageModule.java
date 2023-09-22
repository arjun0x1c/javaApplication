package com.zs.application;
import java.util.ArrayList;

public class MessageModule {
    private MessageDatabase messageDb;

    public MessageModule(MessageDatabase messageDb) {
        this.messageDb = messageDb;
    }

    public void sendMessage(String sender, String receiver, String content) {
        messageDb.sendMessage(new Message(sender, receiver, content));
        System.out.println("Message sent successfully");
    }

    public ArrayList<Message> getMessageForUser(String username) {
        ArrayList<Message> messages = messageDb.getMessageForUser(username);

        if (messages.isEmpty()) {
            System.out.println("No messages found for " + username);
        } else {
            System.out.println("Messages for " + username + ":");
            for (Message message: messages) {
                System.out.println(message.toString());
                System.out.println("--------------------");
            }
        }
        return messages;
    }

    public void deleteMessage(Message message) {
        messageDb.deleteMessage(message);
        System.out.println("Message deleted successfully");
    }
}

