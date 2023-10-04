package com.zs.application;
// import java.util.Scanner;

public class SignupModule {
    private UserDatabase userDb;

    public SignupModule(UserDatabase userDb) {
        this.userDb = userDb;
    }

    public void registerUser(String username, String password, String email) {
        if (userDb.getUserByName(username) != null) {
            System.out.println(ConsoleColors.RED + "\n\t\t[+] Username alredy exists. Please choose a different username." + ConsoleColors.RESET);
        } else {
            User newUser = new User(username, password, email);
            userDb.addUser(newUser);
            System.out.println(ConsoleColors.GREEN + "\n\t\t[+] Successfully Registered. You can now log in." + ConsoleColors.RESET);
        }
    }

    public boolean checkUser(String username) {
        if (userDb.getUserByName(username) != null) {
            return true;
        }
        return false;
    }
}

