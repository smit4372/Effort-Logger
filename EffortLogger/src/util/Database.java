package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dataclass.Log;
import dataclass.Account;

public class Database {
	
	public static void saveLog(Log log) {

		String database = "src/database/logs.txt";

		int token = Encryption.generateToken();
		String content = log.getDate() + "," + log.getStatus() + "," + log.getStartTime() + "," + 
						 log.getEndTime() + "," + log.getProjectName() + "," + log.getCategory() + "," + 
						 Integer.toString(log.getStoryPoints());
		
		try {
			FileWriter fileWriter = new FileWriter(database, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			bufferedWriter.write(log.getName() + "," + Encryption.encrypt(content, token) + "," + Integer.toString(token));
			bufferedWriter.newLine();
            bufferedWriter.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static String getLog(String nameId) {

		String database = "src/database/logs.txt";
		
        try (BufferedReader br = new BufferedReader(new FileReader(database))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                int token = Integer.parseInt(parts[parts.length - 1]);
                String content = parts[1] + "," + parts[2] + "," + parts[3] + "," + 
                				 parts[4] + "," + parts[5] + "," + parts[6] + "," + 
                				 parts[7];

                if (name.equals(nameId)) {
                        return Encryption.decrypt(content, token);
                }
            }
            
        } 
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
	}
	
	public static List<String> getAllNames() {
	    List<String> names = new ArrayList<>();

	    String database = "src/database/logs.txt";

	    try (BufferedReader br = new BufferedReader(new FileReader(database))) {
	        String line;

	        while ((line = br.readLine()) != null) {
	            String[] parts = line.split(",");
	            String name = parts[0];
	            names.add(name);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return names;
	}
	
	public static List<Integer> getCategoryScores() {
	    List<Integer> scores = new ArrayList<>();

	    String database = "src/database/logs.txt";

	    try (BufferedReader br = new BufferedReader(new FileReader(database))) {
	        String line;

	        while ((line = br.readLine()) != null) {
	            String[] parts = line.split(",");
	            Integer score = Integer.parseInt(parts[7]);
	            // TODO: add scores from relevant category only
	            scores.add(score);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return scores;
	}
	
	public static void saveAccount(Account account) {
		String database = "src/database/accounts.txt";
		int token = Encryption.generateToken();
		
		try {
			FileWriter fileWriter = new FileWriter(database, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			bufferedWriter.write(account.getUsername() + "," + Encryption.encrypt(account.getPassword(), token) + "," + Integer.toString(token));
			bufferedWriter.newLine();
            bufferedWriter.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static boolean searchUsernameAndValidate(String searchUsername, String checkPassword) {
		String database = "src/database/accounts.txt";
		
		try (BufferedReader br = new BufferedReader(new FileReader(database))) {
            String line;
            
            while ((line = br.readLine()) != null) {
            	
                String[] parts = line.split(",");
                String username = parts[0];
                String password = parts[1];
                
                int token = Integer.parseInt(parts[2]);
                password = Encryption.decrypt(password, token);
                
                if(username.equals(searchUsername) && password.equals(checkPassword)) {
                	return true;
                } 
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return false;
	}
	
	public static boolean usernameExists(String username) {
        String filteredUsername = InputFilter.filterSpecialCharacters(username);

        if (filteredUsername == null) {
            System.out.println("Invalid input for username.");
            return false;
        }

        String database = "src/database/accounts.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(database))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String existingUsername = InputFilter.filterSpecialCharacters(parts[0]);

                if (existingUsername != null && existingUsername.equals(filteredUsername)) {
                    return true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

	
	public static void main(String[] args) {

	}

}
