package application.dashboard;

import application.definitions.Definitions;
import application.login.Login;
import application.newlog.NewLog;
import application.pastlogs.PastLogs;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DashboardController {
	@FXML private Button newLogButton;
	@FXML private Button pastLogsButton;
	@FXML private Button logOutButton;
	@FXML private Label header;
	@FXML private Button definitionsButton;

	@FXML
    private void initialize() {
		// Call Profile instance from Login class
    	setupNewLogButton();
    	setupPastLogsButton();
    	setupLogOutButton();
    	setupDefinitionsButton();
    	
    	// Update header if username is not null
    }
    
    @FXML
    private void setupNewLogButton() {
    	newLogButton.setOnAction(event -> {
            NewLog newLog = new NewLog();
            newLog.show((Stage) newLogButton.getScene().getWindow());
        });
    }
    
    @FXML
    private void setupPastLogsButton() {
    	pastLogsButton.setOnAction(event -> {
            PastLogs pastLogs = new PastLogs();
            pastLogs.show((Stage) pastLogsButton.getScene().getWindow());
        });
    }
    
    @FXML
    private void setupLogOutButton() {
    	logOutButton.setOnAction(event -> {
            Login login = new Login();
            login.show((Stage) logOutButton.getScene().getWindow());
        });
    }
    
    @FXML
    private void setupDefinitionsButton() {
    	definitionsButton.setOnAction(event -> {
            Definitions definitions = new Definitions();
            definitions.show((Stage) definitionsButton.getScene().getWindow());
        });
    }
}
