package com.example.bookapp;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {

    // Define the salt value
    private static final String SALT = "SUDO";

    public static String generateHash(String input) {
        try {
            // Create a MessageDigest instance for MD5 hashing
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Combine the input with the salt
            String inputWithSalt = input + SALT;

            // Perform the hashing
            byte[] hashBytes = md.digest(inputWithSalt.getBytes());

            // Convert the byte array to a hexadecimal string
            StringBuilder hashString = new StringBuilder();
            for (byte b : hashBytes) {
                hashString.append(String.format("%02x", b));
            }

            return hashString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String input = "myPassword";
        String hashedPassword = generateHash(input);
        System.out.println("Hashed Password: " + hashedPassword);
    }
}
