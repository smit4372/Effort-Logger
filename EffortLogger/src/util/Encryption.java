package util;

import java.util.Random;
import java.lang.StringBuilder;

/**
 * The CaesarCipher class implements a basic Caesar cipher algorithm for encryption and decryption.
 * It shifts each letter in the input text by a fixed number of positions in the alphabet to produce the encrypted result.
 * This class serves as a simplified prototype for testing purposes.
 * 
 * Author: Andrew Houghton
 */

public class Encryption {
	
	public static String encrypt(String content, int token) {
		/**
		 * The encrypt method takes in content (login information, logs, etc.) and a randomly generated token (the shift value).
		 * The value of the token is used to shift the ASCII value of each character in content; content will then be encrypted.
		 * The encrypted content and token are saved to the database by calling the save function.
		 */
		StringBuilder encryptedContent = new StringBuilder();
		String lowercaseContent = content.toLowerCase();
				
		for (int i = 0; i < content.length(); i++) {
			char c = lowercaseContent.charAt(i);
			
			if (Character.isLetter(c)) {
				// The encryption formula
				c = (char) ((c - 'a' + token + 26) % 26 + 'a');
			}
			
			encryptedContent.append(c);
		}
		
		return encryptedContent.toString();
	}
	
	public static String decrypt(String content, int token) {
		/**
		 * The decrypt method undoes the encrypt method by shifting each character back to its original ASCII value based on the token
		 * passed in.
		 */
		StringBuilder decryptedContent = new StringBuilder();
		
		for (int i = 0; i < content.length(); i++) {
			char c = content.charAt(i);
			
			if (Character.isLetter(c)) {
				// The decryption formula
				c = (char) ((c - 'a' - token + 26) % 26 + 'a');
			}
			
			decryptedContent.append(c);
		}
		
		return decryptedContent.toString();
	}
	
	public static int generateToken() {
		/**
		 * A random number from 1 to 26 is generated using the Random class.
		 */
		int token = 0;
		Random random = new Random();
		
		token = random.nextInt(26) + 1;
		
		return token;
	}

	public static void main(String[] args) {
		
	}

}
