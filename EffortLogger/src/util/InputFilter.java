package util;

import java.util.Scanner;

public class InputFilter {

    public static String filterSpecialCharacters(String input) {
        if (input == null) {
            return null; // check if the input is null or not
        }

        String pattern = "[^a-zA-Z0-9 ]"; //This code allows just letters, numbers, and spaces.

        // Swap out all instances of special characters with an empty string
        String filteredInput = input.replaceAll(pattern, "");

        return filteredInput; //return the filtered string 
    }
    
    public static String sanitizeInput(String input) {
        if (input == null) {
            return null;// check if the input is null or not
        }

        // Enable input sanitization by using HTML-escaping special characters which allows to prevent XSS attacks
        return input.replaceAll("&", "&amp;")
                    .replaceAll("<", "&lt;")
                    .replaceAll(">", "&gt;")
                    .replaceAll("\"", "&quot;")
                    .replaceAll("'", "&#39;");
    }

    public static boolean validateInputLength(String input, int maxLength) {
        if (input == null) {
            return false; // check if the input is null or not
        }

        return input.length() <= maxLength;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a string: ");
        String userInput = scanner.nextLine(); //take the input from the user

        String filteredInput = filterSpecialCharacters(userInput);  // call filterSpecialCharacters function 
        															// and send the user input to the function
        														    // for filtration process
        System.out.println("Filtered Input: " + filteredInput);     // print out the filtered string
        
        // Sanitize the user input to escape special characters
        String sanitizedInput = sanitizeInput(userInput);
        System.out.println("Sanitized Input: " + sanitizedInput);

        // Validate Length input length which will limit it to 100 char
        int maxLength = 100;
        boolean isValidLength = validateInputLength(userInput, maxLength);
        System.out.println("Input Length Valid: " + isValidLength);
        
    }
}
