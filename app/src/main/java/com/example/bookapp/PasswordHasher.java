package com.example.bookapp;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {

    // Define the salt value
    private static final String SALT = "SUDO";

    // Function to generate the hash code
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
                hashString.append(String.format("%02x", b)); // Convert to hex
            }

            return hashString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        // Example usage
        String input = "myPassword";
        String hashedPassword = generateHash(input);
        System.out.println("Hashed Password: " + hashedPassword);
    }
}
