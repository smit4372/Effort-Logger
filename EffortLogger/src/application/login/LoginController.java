package application.login;


import javafx.scene.control.TextField;
import application.createaccount.CreateAccount;
import application.dashboard.Dashboard;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import util.InputFilter;

public class LoginController {
	@FXML private Button loginButton;
	@FXML private Button createAccountButton;
	@FXML private TextField usernameTextField;
	@FXML private TextField passwordTextField;
	
	@FXML
    private void initialize() {
    	setupLoginButton();
    	setupCreateAccountButton();
    	// call functions here
    }
   
	@FXML
	private void setupCreateAccountButton() {
	   	createAccountButton.setOnAction(event -> {
	   		CreateAccount createAccount = new CreateAccount();
	   		createAccount.show((Stage) createAccountButton.getScene().getWindow());
	    });
	}
	
    @FXML
    private void setupLoginButton() {
    	
    	loginButton.setOnAction(event -> {
    		Login login = new Login();
    		
    		
        	String username = usernameTextField.getText(); // Retrieve username from input field
            String password = passwordTextField.getText(); // Retrieve password from input field
            
            
            
            if(login.validateLogin(username, password)) {
            	Dashboard dashboard = new Dashboard();
                dashboard.show((Stage) loginButton.getScene().getWindow());
            }else{
            	login.displayError("Invalid credentials. Please try again.");
         	   	usernameTextField.setText("");
         	   	passwordTextField.setText(""); 
            }    
        });
    }
}
