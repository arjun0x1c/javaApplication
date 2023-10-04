package com.zs.application;

public class LoginModule {
    private UserDatabase userDb;

    public LoginModule(UserDatabase userDb) {
        this.userDb = userDb;
    }

    public User login(String username, String password) {
        if (userDb.verifyUser(username, password)) {
            System.out.println(ConsoleColors.GREEN + "\n\t\t[+] Login Successful. Welcome " + username + "!." + ConsoleColors.RESET);
            return userDb.getUserByName(username);
        } else {
            System.out.println(ConsoleColors.RED + "\n\t\t[!] Invalid username or password. Please try again."+ConsoleColors.RESET);
            return null;
        }
    }
}

