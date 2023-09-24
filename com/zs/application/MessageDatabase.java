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

    public MessageDatabase() {
        messages = fetchDatabase();
    }

    public void sendMessage(Message message) {
        messages.add(message);
        updateDatabase(message, true);
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
        if (messages.isEmpty()) {
            try (FileWriter fw = new FileWriter(base + "messages.txt")) {
                fw.write("");
                fw.close();
            } catch (IOException e) {
                System.out.println("\n\t\t[!] Cant delete message");
            }

        } else {
            for (Message message2: messages) {
                updateDatabase(message2, false);
            }
        }
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
                messages.add(new Message(decrypt(message[0]), decrypt(message[1]), decrypt(message[2]), decrypt(message[3])));
            }
        } catch (FileNotFoundException e) {
            System.out.println("\n\t\t[!] message file not found");
        }
        return messages;
    }

    public void updateDatabase(Message message, boolean append) {
        try (FileWriter fw = new FileWriter(base + "messages.txt", append)) {
            fw.write(encrypt(message.getSender()) + ":" + encrypt(message.getReceiver()) + ":" + encrypt(message.getTimestamp()) + ":" + encrypt(message.getContent()) + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("\n\t\t[!] Can't open/write message file.");
        }
    }

    public String encrypt(String message) {
        return Encryptor.iterativeEncrypt(message, Encryptor.generateRandomNumber(25), 1000000);
    }

    public String decrypt(String message) {
        String msg = Encryptor.iterativeDecrypt(message, Encryptor.parseKey(message), 1000000);
        String res = "";
        for (int i=0; i < msg.length(); i++) {
            if (i == 0 || i == msg.length() -1) {
                continue;
            }
            res += msg.charAt(i);
        }
        return res;
    }
}
