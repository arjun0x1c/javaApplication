package com.zs.application;
import java.util.Scanner;

public class SignupModule {
    private UserDatabase userDb;

    public SignupModule(UserDatabase userDb) {
        this.userDb = userDb;
    }

    public void registerUser(String username, String password) {
        if (userDb.getUserByName(username) != null) {
            System.out.println("[+] Username alredy exists. Please choose a different username.");
        } else {
            User newUser = new User(username, password);
            userDb.addUser(newUser);
            System.out.println("[+] Successfully Registered. You can now log in.");
        }
    }
}

