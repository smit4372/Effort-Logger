package application.login;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import util.Database;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

public class Login {
	
	public void show(Stage primaryStage) {
		
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
	}

	public boolean validateLogin(String username, String password) {
		// TODO Auto-generated method stub
		if(sanitizeInput(password)){
			showAlert("Invalid Characters", "Only '@' and '_' are allowed.");
			return false;
		}
		
		if(Database.searchUsernameAndValidate(username, password)){
			return true;
		}
		
		return false;
	}
	
	public void displayError(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }
	

    private static void showAlert(String title, String content) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
    }

    public static boolean sanitizeInput(String input) {
            if (input == null) {
                return false; // return empty string for null input
            }

            // Check for allowed characters, prompt for new input if other characters are detected
            if (!input.matches("[@_a-zA-Z0-9 ]*")) {
                
                return true;
            }

            return false;
    }
        
}
    

