package com.zs.application;

public class PasswordStrengthChecker{
    String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public boolean isValidLength() {
        return password.length() >= 8;
    }

    public boolean hasSpecialChar() {
        String[] chars = {"$","#","@","!","&"};
        boolean flag = false;
        for(String i: chars) {
            if (flag = password.contains(i)){
                break;
            }
        }
        return flag;
    }

    public boolean isDigitPresence() {
        char[] chars = password.toCharArray();
        boolean flag=false;
        for(char i: chars) {
            if(flag = Character.isDigit(i)){
                break;
            }
        }
        return flag;
    }

    public boolean isCaps() {
        char[] ch = password.toCharArray();
        boolean flag=false;
        for(char i: ch) {
            if( flag = Character.isUpperCase(i) ){
                break;
            }
        }
        return flag;
    }
    public boolean identifyPasswordStrength() {
        if(isValidLength() && hasSpecialChar() && isDigitPresence() && isCaps() ){
            // System.out.println("\n\t\t[+] Your Password is Strong.");
            return true;
        }
        else {
            System.out.println(ConsoleColors.RED + "\n\t\t[!] Your password is not strong.\n" + ConsoleColors.RESET);
            if(!isValidLength()){
                System.out.println(ConsoleColors.RED + "\t\t* Password must be more than 8 chars" + ConsoleColors.RESET);
            }
            if(!hasSpecialChar()){
                System.out.println(ConsoleColors.RED + "\t\t* Password must contain one speacial char" + ConsoleColors.RESET);
            }
            if(!isDigitPresence()){
                System.out.println(ConsoleColors.RED + "\t\t* Password must have a one digit" + ConsoleColors.RESET);
            }
            if(!isCaps()){
                System.out.println(ConsoleColors.RED + "\t\t* Password must have one uppercase" + ConsoleColors.RESET);
            }
            return false;
        }
    }
}