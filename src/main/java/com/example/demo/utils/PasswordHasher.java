package com.example.demo.utils;  // or com.example.demo.security

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {

    // Method to hash a password
    public static String hashPassword(String password) {
        try {
            // Create MessageDigest instance for SHA-256 algorithm
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());

            // Convert byte array to hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString(); // Return hashed password
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null; // Return null in case of error
        }
    }
}
