package com.zs.application;

public class Encryptor {
    public static String encrypt(String message, int shift) {
        String encryptedMessage = "";

        for(int i=0; i < message.length(); i++) {
            char ch = message.charAt(i);

            if(Character.isLetter(ch)) {
                char base = Character.isLowerCase(ch) ? 'a' : 'A';
                char encryptedChar = (char) (base + (ch - base + shift) % 26);
                encryptedMessage += encryptedChar;
            } else {
                encryptedMessage += ch;
            }
        }

        return encryptedMessage;
    }

    public static String decrypt(String message, int shift) {
        String decryptedMessage = "";

        for(int i=0; i < message.length(); i++) {
            char ch = message.charAt(i);

            if(Character.isLetter(ch)) {
                char base = Character.isLowerCase(ch) ? 'a' : 'A';
                char decryptedChar = (char) (base + (ch - base - shift + 26) % 26);
                decryptedMessage += decryptedChar;
            } else {
                decryptedMessage += ch;
            }
        }

        return decryptedMessage;
    }

    public static int generateRandomNumber(int max) {
        int min = 5;
        int shift;
        while( (shift = (int) (Math.random() * (max - min + 1)) + min) == 13){
            continue;
        }
        return shift;
    }

    public static String iterativeEncrypt(String message, int shift, int iteration) {
        String result = message;

        for(int i=0; i < iteration; i++) {
            result = encrypt(result, shift);
        }

        return bindKey(result, shift);
    }

    public static String iterativeDecrypt(String message, int shift, int iteration) {
        String result = message;

        for(int i=0; i < iteration; i++) {
            result = decrypt(result, shift);
        }

        return result;
    }

    public static int parseKey(String message) {
        String result = message.split("")[0] + message.split("")[message.length()-1];
        return Integer.valueOf(result);
    }

    public static String bindKey(String message, int key) {
        if (key < 10) {
            message = "0" + message + String.valueOf(key);
        } else {
            String strKey = String.valueOf(key);
            message = strKey.charAt(0) + message + strKey.charAt(1);
        }
        return message;
    }
}
