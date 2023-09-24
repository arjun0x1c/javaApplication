package com.zs.application;

public class LoginModule {
    private UserDatabase userDb;

    public LoginModule(UserDatabase userDb) {
        this.userDb = userDb;
    }

    public User login(String username, String password) {
        if (userDb.verifyUser(username, password)) {
            System.out.println("\n\t\t[+] Login Successful. Welcome " + username + "!.");
            return userDb.getUserByName(username);
        } else {
            System.out.println("\n\t\t[!] Invalid username or password. Please try again.");
            return null;
        }
    }
}

