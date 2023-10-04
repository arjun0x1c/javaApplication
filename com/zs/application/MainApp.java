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
            System.out.println("\n\n\t" + ConsoleColors.YELLOW +"Welcome to the app");
            System.out.println("\t1. Signup.");
            System.out.println("\t2. Log in");
            System.out.println("\t0. Exit");
            System.out.print("\n\tEnter your choice[0/1/2]: " + ConsoleColors.GREEN);

            int choice;
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                sc.nextLine();
                System.out.println(ConsoleColors.RED + "\n\t\tPlease give numbers only." + ConsoleColors.RESET);
                continue;
            }

            switch (choice) {
                case 0:
                    System.out.println(ConsoleColors.GREEN + "\n\t\tGood Bye!!" + ConsoleColors.RESET);
                    sc.close();
                    System.exit(0);
                case 1:
                    System.out.print("\n\t" + ConsoleColors.YELLOW + "[+] Enter your username: " + ConsoleColors.GREEN);
                    String signupUsername = sc.nextLine();
                    if (signupModule.checkUser(signupUsername)) {
                        System.out.println("\n\t\t" + ConsoleColors.RED + "[!] User already exist. Try another username." + ConsoleColors.RESET);
                        break;
                    }
                    System.out.print(ConsoleColors.YELLOW + "\t[+] Enter your email: " + ConsoleColors.GREEN);
                    String signupEmail = sc.nextLine();
                    String signupPassword = readPassword(ConsoleColors.YELLOW + "[+] Enter your password: ");
                    PasswordStrengthChecker pc = new PasswordStrengthChecker();
                    pc.setPassword(signupPassword);
                    if (!pc.identifyPasswordStrength()) {
                        break;
                    }
                    String confirmPassword = readPassword(ConsoleColors.YELLOW + "[+] Confirm Password: ");
                    if (!signupPassword.equals(confirmPassword)) {
                        System.out.println(ConsoleColors.RED + "\n\t\t[!] Password Not matched." + ConsoleColors.RESET);
                        break;
                    }
                    signupModule.registerUser(signupUsername, signupPassword, signupEmail);
                    break;
                case 2:
                    System.out.print(ConsoleColors.YELLOW + "\n\t[+] Enter your username: " + ConsoleColors.GREEN);
                    String loginUsername = sc.nextLine();
                    String loginPassword = readPassword(ConsoleColors.YELLOW + "[+] Enter your password: ");
                    User loggedInUser = loginModule.login(loginUsername, loginPassword);
                    if (loggedInUser != null) {
                        handleLogedInUser(loggedInUser, messageModule, sc, userDb);
                    }
                    break;
                default:
                    System.out.println(ConsoleColors.RED + "\n\t\t[!] Invalid choice. Please try again." + ConsoleColors.RESET);
                    break;
            }
        }
    }

    public static String readPassword(String message) {
        Console console = System.console();

        String result = "";
        if (console == null) {
            System.out.println(ConsoleColors.RED + "\n\t\t[!] Can't open a console." + ConsoleColors.RESET);
        } else {
            result = String.valueOf(console.readPassword("\t" + message));
        }
        return result;
    }

    public static void handleLogedInUser(User user, MessageModule messageModule, Scanner sc, UserDatabase userDb) {
        boolean flag = true;
        int unreadMessages = messageModule.getUnreadMessagesCount(user.getUsername());
        
        if (unreadMessages>0) {
            System.out.println(ConsoleColors.BLUE + "\n\t\t[+] You have " + unreadMessages + " unread messages." + ConsoleColors.RESET);
        }

        while (flag) {
            System.out.println(ConsoleColors.YELLOW + "\n\n\t1. Send message.");
            System.out.println("\t2. view message.");
            System.out.println("\t3. view users.");
            System.out.println("\t4. Delete messages.");
            System.out.println("\t0. Logout.");
            System.out.print("\n\tEnter your choice: " + ConsoleColors.GREEN);

            int ch;
            try {
                ch = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                sc.nextLine();
                System.out.println(ConsoleColors.RED + "\n\t\tPlease give numbers only." + ConsoleColors.RESET);
                continue;
            }

            switch (ch) {
                case 0:
                    System.out.println(ConsoleColors.GREEN + "\n\t\tSuccessfully Logged out." + ConsoleColors.RESET);
                    flag = false;
                    break;
                case 1:
                    System.out.print(ConsoleColors.YELLOW + "\n\tTo: " + ConsoleColors.GREEN);
                    String receiverName = sc.nextLine();
                    if (userDb.getUserByName(receiverName) == null) {
                        System.out.println(ConsoleColors.RED + "\n\t\t[!] User not Found." + ConsoleColors.RESET);
                        break;
                    } else if (receiverName.equals(user.getUsername())) {
                        System.out.println(ConsoleColors.RED + "\n\t\t[!] Can't send message to same user." + ConsoleColors.RESET);
                        break;
                    }
                    System.out.print(ConsoleColors.YELLOW + "\tContent: " + ConsoleColors.GREEN);
                    String messageContent = sc.nextLine();

                    messageModule.sendMessage(user.getUsername(), receiverName, messageContent);
                    break;
                case 2:
                    System.out.println(ConsoleColors.YELLOW + "\n\t1. view unread messages.");
                    System.out.println("\t2. view All messages.");
                    System.out.println("\t0. Back.");
                    System.out.print("\t[+] Enter option[0/1/2]: " + ConsoleColors.GREEN);

                    try {
                        int n = sc.nextInt();
                        sc.nextLine();

                        switch (n) {
                            case 0:
                                break;
                            case 1:
                                messageModule.getUnreadMessages(user.getUsername());
                                break;
                            case 2:
                                messageModule.getMessageForUser(user.getUsername());
                                break;
                            default:
                                System.out.println(ConsoleColors.RED + "\n\t\t[!] Wrong option. Back to main menu." + ConsoleColors.RESET);
                                break;
                        }
                    } catch (Exception e) {
                        sc.nextLine();
                        System.out.println(ConsoleColors.RED + "\n\t\t[!] Please give numbers only." + ConsoleColors.RESET);
                    }

                    break;
                case 3:
                    ArrayList<User> users = userDb.getUser();
                    System.out.println(ConsoleColors.BLUE + "\n\tRegisterd users.");
                    for (User registeredUser : users) {
                        System.out.println(ConsoleColors.GREEN + "\t" + registeredUser.getUsername() + ConsoleColors.RESET);
                    }
                    break;
                case 4:
                    ArrayList<Message> messages = messageModule.getMessageForUser(user.getUsername());
                    if (!messages.isEmpty()) {
                        System.out.print(ConsoleColors.YELLOW + "\n\tEnter the message id to delete\n\t\t(or)\n\tEnter 0 to delete all messages\n\t\t(or)\n\tEnter -1 to Back main menu.\n\t[+] Enter option: " + ConsoleColors.GREEN);
                    } else {
                        break;
                    }

                    try {
                        int n = sc.nextInt();
                        sc.nextLine();
                        if (n == -1) {
                            System.out.println(ConsoleColors.BLUE + "\n\t\t[+] Back to main menu..." + ConsoleColors.RESET);
                            break;
                        } else if (n == 0) {
                            messageModule.deleteAll();
                        } else {
                            for (Message message: messages) {
                                if (message.getId() == n) {
                                    messageModule.deleteMessage(message);
                                }
                            }
                        }
                    } catch (Exception e) {
                        sc.nextLine();
                        System.out.println(ConsoleColors.RED + "\n\t\tPlease enter numbers only" + ConsoleColors.RESET);
                    }
                    break;
                default:
                    System.out.println(ConsoleColors.RED + "\n\t\tWrong choice. Please try again." + ConsoleColors.RESET);
                    break;
            }
        }
    }
}