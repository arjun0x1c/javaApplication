package com.zs.application;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserDatabase {
    private String base = "/home/arjun/javaClass/javaApplication/com/zs/application/";
    private ArrayList<User> users;

    public UserDatabase() {
        users = fetchDatabase();
    }

    public void addUser(User user) {
        users.add(user);
        updateDatabase(user);
    }

    public User getUserByName(String username) {
        for(User user: users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean verifyUser(String username, String password) {
        User user = getUserByName(username);
        return user != null &&  user.getPassword().equals(password);
    }

    public ArrayList<User> getUser() {
        return users;
    }

    public ArrayList<User> fetchDatabase() {
        ArrayList<User> users = new ArrayList<>();

        try( Scanner sc = new Scanner(new File(base + "users.txt"))){
            while (sc.hasNextLine()) {
                String[] user = sc.nextLine().split(":");
                users.add(new User(decrypt(user[0]), decrypt(user[1]), decrypt(user[2])));
            }   
        } catch (Exception e) {
            System.out.println("\n\t\t[!] users file not found.");
        }
        return users;
    }

    public void updateDatabase(User user) {
        try (FileWriter fw = new FileWriter(base + "users.txt", true)) {
            fw.write(encrypt(user.getUsername()) + ":" + encrypt(user.getPassword()) + ":" + encrypt(user.getEmail()) + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Cant write/open users file");
        }
    }

    public String encrypt(String message) {
        return Encryptor.iterativeEncrypt(message, Encryptor.generateRandomNumber(25), 1000000);
    }

    public String decrypt(String message) {
        String msg = Encryptor.iterativeDecrypt(message, Encryptor.parseKey(message), 1000000);
        String res = "";
        for (int i=0; i < msg.length(); i++) {
            if (i == 0 || i == msg.length()-1) {
                continue;
            }
            res += msg.charAt(i);
        }
        return res;
    }
}
