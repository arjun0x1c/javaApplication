package com.zs.application;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.Console;

public class MainApp {
    public static void main(String[] args) {
        UserDatabase userDb = new UserDatabase();
        MessageDatabase messageDb = new MessageDatabase();
        SignupModule signupModule = new SignupModule(userDb);
        LoginModule loginModule = new LoginModule(userDb);
        MessageModule messageModule = new MessageModule(messageDb);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n\n\tWelcome to the app");
            System.out.println("\t1. Signup.");
            System.out.println("\t2. Log in");
            System.out.println("\t0. Exit");
            System.out.print("\n\tEnter your choice: ");

            int choice;
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                sc.nextLine();
                System.out.println("\n\t\tPlease give numbers only.");
                continue;
            }

            switch (choice) {
                case 0:
                    System.out.println("\n\t\tGood Bye!!");
                    sc.close();
                    System.exit(0);
                case 1:
                    System.out.print("\n\t[+] Enter your username: ");
                    String signupUsername = sc.nextLine();
                    System.out.print("\t[+] Enter your email: ");
                    String signupEmail = sc.nextLine();
                    String signupPassword = readPassword("[+] Enter your password: ");
                    PasswordStrengthChecker pc = new PasswordStrengthChecker();
                    pc.setPassword(signupPassword);
                    if (!pc.identifyPasswordStrength()) {
                        break;
                    }
                    String confirmPassword = readPassword("[+] Confirm Password: ");
                    if (!signupPassword.equals(confirmPassword)) {
                        System.out.println("\n\t\t[!] Password Not matched.");
                        break;
                    }
                    signupModule.registerUser(signupUsername, signupPassword, signupEmail);
                    break;
                case 2:
                    System.out.print("\n\t[+] Enter your username: ");
                    String loginUsername = sc.nextLine();
                    String loginPassword = readPassword("[+] Enter your password: ");
                    User loggedInUser = loginModule.login(loginUsername, loginPassword);
                    if (loggedInUser != null) {
                        handleLogedInUser(loggedInUser, messageModule, sc, userDb);
                    }
                    break;
                default:
                    System.out.println("\n\t\t[!] Invalid choice. Please try again.");
                    break;
            }
        }
    }

    public static String readPassword(String message) {
        Console console = System.console();

        String result = "";
        if (console == null) {
            System.out.println("\n\t\t[!] Can't open a console.");
        } else {
            result = String.valueOf(console.readPassword("\t" + message));
        }
        return result;
    }

    public static void handleLogedInUser(User user, MessageModule messageModule, Scanner sc, UserDatabase userDb) {
        boolean flag = true;
        while (flag) {
            System.out.println("\n\n\t1. Send message.");
            System.out.println("\t2. view message.");
            System.out.println("\t3. view users.");
            System.out.println("\t4. Delete messages.");
            System.out.println("\t0. Logout.");
            System.out.print("\n\tEnter your choice: ");

            int ch;
            try {
                ch = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                sc.nextLine();
                System.out.println("\n\t\tPlease give numbers only.");
                continue;
            }

            switch (ch) {
                case 0:
                    System.out.println("\n\t\tSuccessfully Logged out.");
                    flag = false;
                    break;
                case 1:
                    System.out.print("\n\tTo: ");
                    String receiverName = sc.nextLine();
                    if (userDb.getUserByName(receiverName) == null) {
                        System.out.println("\n\t\t[!] User not Found.");
                        break;
                    } else if (receiverName == user.getUsername()) {
                        System.out.println("\n\t\t[!] Can't send message to same user.");
                        break;
                    }
                    System.out.print("\tContent: ");
                    String messageContent = sc.nextLine();

                    messageModule.sendMessage(user.getUsername(), receiverName, messageContent);
                    break;
                case 2:
                    messageModule.getMessageForUser(user.getUsername());
                    break;
                case 3:
                    ArrayList<User> users = userDb.getUser();
                    System.out.println("\n\tRegisterd users.");
                    for (User registeredUser : users) {
                        System.out.println("\t" + registeredUser.getUsername());
                    }
                    break;
                case 4:
                    ArrayList<Message> messages = messageModule.getMessageForUser(user.getUsername());
                    if (!messages.isEmpty()) {
                        System.out.print("\n\tEnter no.of message to delete\n\t(or)\n\tEnter 0 to delete all messages\\-1 to cancel: ");
                    } else {
                        break;
                    }

                    try {
                        int n = sc.nextInt();
                        sc.nextLine();
                        if (n == -1) {
                            System.out.println("\n\t\t[+] cancelled.");
                            break;
                        } else if (n == 0) {
                            messageModule.deleteAll();
                        } else {
                            for (int i = 0; i < messages.size(); i++) {
                                if (i == n - 1 && n > 0) {
                                    messageModule.deleteMessage(messages.get(i));
                                }
                            }
                        }
                    } catch (Exception e) {
                        sc.nextLine();
                        System.out.println("\n\t\tPlease enter numbers only");
                    }
                    break;
                default:
                    System.out.println("\n\t\tWrong choice. Please try again.");
                    break;
            }
        }
    }
}