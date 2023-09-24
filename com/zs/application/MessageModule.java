package com.zs.application;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class MessageModule {
    private MessageDatabase messageDb;

    public MessageModule(MessageDatabase messageDb) {
        this.messageDb = messageDb;
    }

    public void sendMessage(String sender, String receiver, String content) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH.mm.ss");
        messageDb.sendMessage(new Message(sender, receiver, formatter.format(date), content));
        System.out.println("\n\t\t[+] Message sent successfully");
    }

    public ArrayList<Message> getMessageForUser(String username) {
        ArrayList<Message> messages = messageDb.getMessageForUser(username);

        if (messages.isEmpty()) {
            System.out.println("\n\t\t[!] No messages found for " + username);
        } else {
            System.out.println("\n\tMessages for " + username + ":\n");
            for (Message message: messages) {
                System.out.println("\t" + message.toString());
                System.out.println("\t--------------------");
            }
        }
        return messages;
    }

    public void deleteMessage(Message message) {
        messageDb.deleteMessage(message);
        System.out.println("\n\t\t[+] Message deleted successfully");
    }

    public void deleteAll() {
        messageDb.deleteAll();
        System.out.println("\n\t\t[+] Deleted all messages.");
    }
}

