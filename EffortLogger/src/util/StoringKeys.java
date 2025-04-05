package util;

//Importing the needed files
import java.util.Scanner;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;

/*
Name - Tanmay Sureshkumar Chaudhari
Class - CSE360 
Description - This code generates an encrypted key for a 
user with a particular role and then asks if they want to rotate that key and then it stores in text file. 
*/

public class StoringKeys {
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	
	//Main method
    public static void main(String[] args) throws Exception {
        
    	// Creating a Scanner objects for User Inputs
        Scanner scanner = new Scanner(System.in);

        
        // Asking user for input 
        System.out.print("Enter your name: ");
        String userName = scanner.nextLine();
        System.out.print("Enter your role: ");
        String userRole = scanner.nextLine();

        // Generating a new encryption key
        String key = generateKey(16);

        // Printing the generated key
        System.out.println("Generated key for " + userName + " (" + userRole + "): " + key);

        // Storing the key securely
        storeKey(key, userName, userRole);

        // Asking the user if they want to rotate the key
        System.out.print("\nDo you want to rotate the key? (yes/no): ");
        String response = scanner.nextLine();
        
        if (response.equalsIgnoreCase("yes")) 
        {
            // Rotating the key
            rotateKey(userName, userRole);
        }
        
        scanner.close();
    }

    // Function to generate and return a new encryption key
    public static String generateKey(int length) 
    {
        SecureRandom random = new SecureRandom();
        StringBuilder build = new StringBuilder(length);
        
        for (int i = 0; i < length; i++) 
        {
            int index = random.nextInt(CHARACTERS.length());
            build.append(CHARACTERS.charAt(index));
        }
        
        return build.toString();
    }

    // Function to store the key securely
    public static void storeKey(String key, String userName, String userRole) throws IOException 
    {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = Instant.now().atZone(ZoneId.systemDefault()).format(formatter);
    	
        String data = "User: " + userName + "\n" + "Role: " + userRole + "\n" + "Key: " + key + "\n" + "Time: " + formattedTime + "\n\n";

        //Creating a file to store the data.
        File file = new File("keys.txt");
        
        
        if (file.exists()) 
        {
            // If file exists then store the data in it
            String content = new String(Files.readAllBytes(Paths.get("keys.txt")));
            data += content;
        }

        try (FileWriter writer = new FileWriter("keys.txt")) 
        {
            writer.write(data);
            System.out.println("\nKey for " + userName + " (" + userRole + ") stored successfully.");
            
        } catch (IOException e) 
        {
            System.out.println("\nAn error occurred while storing the key.");
            e.printStackTrace();
        }
    }

    // Rotating the key
    public static void rotateKey(String userName, String userRole) throws Exception 
    {
        System.out.println("\nRotating key for " + userName + " (" + userRole + ")");
        String newKey = generateKey(16);
        System.out.println("\nNew rotated key for " + userName + " (" + userRole + "): " + newKey);
        storeKey(newKey, userName, userRole);
    }
}
