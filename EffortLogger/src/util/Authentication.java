package util;

import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Authentication {
	
    private HashMap<String, User> users = new HashMap<>();

    // User class to hold role, key, and MFA status
    public static class User {
        String role;
        String key;
        boolean isAuthenticated;

        public User(String role, String key, boolean isAuthenticated) {
            this.role = role;
            this.key = key;
            this.isAuthenticated = isAuthenticated;
        }
    }

    // Access Control
    public boolean hasAccess(String name, String role, String key) {
        User user = users.get(name);
        return user != null && user.role.equals(role) && user.key.equals(key) && user.isAuthenticated;
    }

    // Multi-Factor Authentication (MFA)
    public void authenticate(String name, boolean isAuthenticated) {
        if (users.containsKey(name)) {
            users.get(name).isAuthenticated = isAuthenticated;
        }
    }

    // Add user to the system
    public void addUser(String name, String role) {
        // Generate a key using Caesar Cipher
        String key = generateCaesarCipherKey(name, role);
        users.put(name, new User(role, key, false));
        System.out.println("Your generated key is: " + key);
    }

    // Generate a Caesar Cipher key based on the user's name and role
    private String generateCaesarCipherKey(String name, String role) {
        String base = "YourBaseStringHere";
        int shift = (name.length() + role.length()) % 26;
        return caesarCipher(base, shift);
    }

    // Implementing Caesar Cipher
    private String caesarCipher(String value, int shift) {
        StringBuilder result = new StringBuilder();
        for (char character : value.toCharArray()) {
            if (character != ' ') {
                int originalAlphabetPosition = character - 'A';
                int newAlphabetPosition = (originalAlphabetPosition + shift) % 26;
                char newCharacter = (char) ('A' + newAlphabetPosition);
                result.append(newCharacter);
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }

    // Main method
    public static void main(String[] args) throws IOException {
        Authentication security = new Authentication();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your role: ");
        String role = scanner.nextLine();

        security.addUser(name, role);
        security.authenticate(name, true);

        try (FileWriter out = new FileWriter("users.txt", true)) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            out.write("\nTime: " + dtf.format(now) + "\n");

            for (Map.Entry<String, User> entry : security.users.entrySet()) {
                out.write(entry.getKey() + ": " + entry.getValue().role + "\n");
            }
        }

        boolean accessGranted = false;
        while (!accessGranted) {
            System.out.print("\nEnter your name for access verification: ");
            String accessName = scanner.nextLine();

            System.out.print("Enter your role for access verification: ");
            String accessRole = scanner.nextLine();

            System.out.print("Enter the key to check access: ");
            String accessKey = scanner.nextLine();

            if (security.hasAccess(accessName, accessRole, accessKey)) {
                System.out.println("Access granted.\n");
                accessGranted = true;
            } else {
                System.out.println("Access denied. Please try again.");
            }
        }

        scanner.close();
    }
}
