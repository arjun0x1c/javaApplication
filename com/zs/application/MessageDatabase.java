package com.zs.application;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MessageDatabase { 
    private String base = "/home/arjun/javaClass/javaApplication/com/zs/application/";
    private ArrayList<Message> messages;
    private ArrayList<Message> unreadMessages = new ArrayList<>();
    private ArrayList<Message> modifiedMessages = new ArrayList<>();
    private ArrayList<Message> deletedMessages = new ArrayList<>();

    public MessageDatabase() {
        messages = fetchDatabase();
        intializeUnreadMessages();
    }

    public void sendMessage(Message message) {
        unreadMessages.add(message);
        modifiedMessages.add(message);
    }

    public void intializeUnreadMessages() {
        for (Message message: messages) {
            if (!message.isRead()) {
                unreadMessages.add(message);
            }
        }
    }
    public ArrayList<Message> getMessageForUser(String username) {
        ArrayList<Message> messagess = new ArrayList<>(messages);
        ArrayList<Message> userMessages = new ArrayList<>();

        for (Message message: messagess) {
            if (message.getReceiver().equals(username)) {
                userMessages.add(message);
                if (!message.isRead()) {
                    markAsRead(message);
                }
            }
        }
        messagess.clear();
        return userMessages;
    }

    public ArrayList<Message> getUnreadMessages(String username) {
        ArrayList<Message> uMessages = new ArrayList<>(unreadMessages);
        ArrayList<Message> userUnreadMessages = new ArrayList<>();

        for(Message message: uMessages) {
            if (message.getReceiver().equals(username)) {
                userUnreadMessages.add(message);
                markAsRead(message);
            }
        }
        uMessages.clear();
        return userUnreadMessages;
    }

    public int getUnreadMessagesCount(String username) {
        int counter = 0;

        for (Message message: messages) {
            if (message.getReceiver().equals(username) && !message.isRead()) {
                counter++;
            }
        }
        return counter;
    }

    public void deleteMessage(Message message) {
        messages.remove(message);
        deletedMessages.add(message);
    }

    public void deleteAll() {
        messages.removeAll(messages);
        try (FileWriter fw = new FileWriter(base + "messages.txt")) {
            fw.write("");
            fw.close();
        } catch (IOException e) {
            System.out.println("\n\t\t[!] Cant delete message");
        }
    }

    public ArrayList<Message> fetchDatabase() {
        ArrayList<Message> messages = new ArrayList<>();

        try (Scanner sc = new Scanner(new File(base + "messages.txt"))) {
            while (sc.hasNextLine()) {
                String[] message = sc.nextLine().split(":");

                boolean bool = Boolean.parseBoolean(decrypt(message[4]));

                messages.add(new Message(decrypt(message[0]), decrypt(message[1]), decrypt(message[2]), decrypt(message[3]), bool));
            }
        } catch (FileNotFoundException e) {
            System.out.println("\n\t\t[!] message file not found");
        }
        return messages;
    }

    public void updateDatabase() {
        for (Message deletedMessage: deletedMessages) {
            messages.removeIf(existingMessage -> existingMessage.getId() == deletedMessage.getId());
        }

        if (modifiedMessages.isEmpty()) {
            return;
        }

        try (FileWriter fw = new FileWriter(base + "messages.txt", false)) {
            for (Message existingMessage: messages) {
                String line = encrypt(existingMessage.getSender()) + ":" + encrypt(existingMessage.getReceiver()) + ":" + encrypt(existingMessage.getTimestamp()) + ":" + encrypt(existingMessage.getContent()) + ":" + encrypt(String.valueOf(existingMessage.isRead())) + "\n";
                fw.write(line);
            }

            for (Message modifiedMessage: modifiedMessages) {
                String line = encrypt(modifiedMessage.getSender()) + ":" + encrypt(modifiedMessage.getReceiver()) + ":" + encrypt(modifiedMessage.getTimestamp()) + ":" + encrypt(modifiedMessage.getContent()) + ":" + encrypt(String.valueOf(modifiedMessage.isRead())) + "\n";
                fw.write(line);
                messages.add(modifiedMessage);
            }
            fw.close();
            modifiedMessages.clear();
            deletedMessages.clear();
        } catch (IOException e) {
            System.out.println("\n\t\t[!] Can't open/write existinMessage file.");
        }
    }

    public String encrypt(String message) {
        return Encryptor.iterativeEncrypt(message, Encryptor.generateRandomNumber(25), 100000);
    }

    public String decrypt(String message) {
        String msg = Encryptor.iterativeDecrypt(message, Encryptor.parseKey(message), 100000);
        String res = "";
        for (int i=0; i < msg.length(); i++) {
            if (i == 0 || i == msg.length() -1) {
                continue;
            }
            res += msg.charAt(i);
        }
        return res;
    }

    public void markAsRead(Message message) {
        message.setRead(true);
        unreadMessages.remove(message);
        modifiedMessages.add(message);
        messages.remove(message);
    }
}