package com.zs.application;
import java.util.Scanner;
import java.util.ArrayList;

public class MainApp {
    public static void main(String[] args) {
        UserDatabase userDb = new UserDatabase();
        MessageDatabase messageDb = new MessageDatabase();
        SignupModule signupModule = new SignupModule(userDb);
        LoginModule loginModule = new LoginModule(userDb);
        MessageModule messageModule = new MessageModule(messageDb);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to the app");
            System.out.println("1. Signup.");
            System.out.println("2. Log in");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Please give numbers only.");
                continue;
            }

            switch (choice) {
                case 0:
                    System.out.println("Good Bye!!");
                    sc.close();
                    System.exit(0);
                case 1:
                    System.out.print("Enter your username: ");
                    String signupUsername = sc.nextLine();
                    System.out.print("Enter your password: ");
                    String signupPassword = sc.nextLine();
                    signupModule.registerUser(signupUsername, signupPassword);
                    break;
                case 2:
                    System.out.print("Enter your username: ");
                    String loginUsername = sc.nextLine();
                    System.out.print("Enter your password: ");
                    String loginPassword = sc.nextLine();
                    User loggedInUser = loginModule.login(loginUsername, loginPassword);
                    if (loggedInUser != null) {
                        handleLogedInUser(loggedInUser, messageModule, sc);
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
                }
            }
        }

    public static void handleLogedInUser(User user, MessageModule messageModule, Scanner sc) {
        boolean flag = true;
        while (flag) {
            System.out.println("1. Send message.");
            System.out.println("2. view message.");
            System.out.println("3. view users.");
            System.out.println("0. Logout.");
            System.out.print("Enter your choice: ");

            int ch;
            try {
                ch = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Please give numbers only.");
                continue;
            }

            switch (ch) {
                case 0:
                    System.out.println("Successfully Logged out.");
                    flag = false;
                    break;
                case 1:
                    System.out.print("To: ");
                    String receiverName = sc.nextLine();
                    System.out.print("Content: ");
                    String messageContent = sc.nextLine();
                    
                    messageModule.sendMessage(user.getUsername(), receiverName, messageContent);
                    break;
                case 2:
                    messageModule.getMessageForUser(user.getUsername());
                    break;
                case 3:
                    ArrayList<Message> messages = messageModule.getMessageForUser(user.getUsername());
                    System.out.print("Enter no.of message to delete\n\t(or)\nEnter 0 to delete all messages:");
                    int n=0;
                    try {
                        n = sc.nextInt();
                        sc.nextLine();
                    } catch (Exception e) {
                        System.out.println("Please enter numbers only");
                    }

                    for (int i=0; i < messages.size(); i++) {
                        if (i == n-1 && n > 0) {
                            messageModule.deleteMessage(messages.get(i));
                        }
                    }
                    break;
                default:
                    System.out.println("Wrong choice. Please try again.");
                    break;
                }
            }
        }
}