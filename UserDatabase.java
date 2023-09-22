package com.zs.application;
import java.util.ArrayList;

public class UserDatabase {
    private ArrayList<User> users;

    public UserDatabase() {
        users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
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
}
