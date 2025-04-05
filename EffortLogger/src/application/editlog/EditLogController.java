package application.editlog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import application.pastlogs.PastLogs;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class EditLogController<Strings> implements Initializable{
	@FXML 
	private Button cancelButton;
	
	@FXML private TextField logNameTextField;
	
	@FXML private TextField storyPointsTextField;
	
	@FXML private TextField endTimeTextField; 
	
	@FXML private TextField startTimeTextField;
	
	@FXML private DatePicker datePicker;
    @FXML
    private ChoiceBox<String> statusChoiceBox;
    
    @FXML
    private ChoiceBox<String> categoryChoiceBox;
    
    @FXML 
    private ChoiceBox<String> projectNameChoiceBox;
    
    @FXML 
    private ChoiceBox<String> lifeCycleChoiceBox;
    
    
    
    @FXML
    private Button saveButton;
    
    public void initialize(URL arg0, ResourceBundle arg1) {
        setupCancelButton();
        setupSaveButton();
        
        statusChoiceBox.getItems().add((String) "In Progress");
    	statusChoiceBox.getItems().add((String) "Completed");
    	
    	categoryChoiceBox.getItems().add((String) "Effort");
    	categoryChoiceBox.getItems().add((String) "Defect");
    	
    	// Read project names and populate ProjectNameChoiceBox
        List<String> projectNames = readProjectNamesFromFile();
        projectNameChoiceBox.getItems().addAll(projectNames);

        // Read lifecycle steps and populate LifeCycleChoiceBox
        List<String> lifecycleSteps = readLifecycleStepsFromFile();
        lifeCycleChoiceBox.getItems().addAll(lifecycleSteps);
    	
    	
    }
    
    private List<String> readProjectNamesFromFile() {
        // Replace with your actual file path
    	
   
        String filePath = "src/database/definitions.txt";
        List<String> projectNames = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Project Name:")) {
                    // Extract project names from the line and add to the list
                    String[] parts = line.split(":")[1].split(",");
                    projectNames.addAll(Arrays.asList(parts));
                    break; // Assuming there is only one line with project names
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return projectNames;
    }

    private List<String> readLifecycleStepsFromFile() {
        // Replace with your actual file path
        String filePath = "src/database/definitions.txt";
    	
        
        List<String> lifecycleSteps = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Lifecycle Step:")) {
                    // Extract lifecycle steps from the line and add to the list
                    String[] parts = line.split(":")[1].split(",");
                    lifecycleSteps.addAll(Arrays.asList(parts));
                    break; // Assuming there is only one line with lifecycle steps
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lifecycleSteps;
    }

	@FXML
    private void setupCancelButton() {
    	cancelButton.setOnAction(event -> {
            PastLogs pastLogs = new PastLogs();
            pastLogs.show((Stage) cancelButton.getScene().getWindow());
        });
    }
	
	@FXML
    private void setupSaveButton() {
        saveButton.setOnAction(event -> {
            try {
                String lognameToEdit = logNameTextField.getText();
                String newStatus = statusChoiceBox.getValue();
                String newDate = datePicker.getValue().toString();
                String newStartTime = startTimeTextField.getText();
                String newEndTime = endTimeTextField.getText();
                String newCategory = categoryChoiceBox.getValue();
                String newProjectName = projectNameChoiceBox.getValue();
                String newLifeCycleStep = lifeCycleChoiceBox.getValue();
                String newStoryPoints = storyPointsTextField.getText();
                updateDatabase(lognameToEdit, newStatus, newDate, newStartTime, newEndTime, newCategory, newLifeCycleStep, newProjectName, newStoryPoints);
                System.out.println("Database updated successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

	private void updateDatabase(String lognameToEdit, String newStatus, String newDate, String newStartTime,
	        String newEndTime, String newCategory, String newLifeCycleStep, String newProjectName, String newStoryPoints)
	        throws IOException {
	    String filePath = "src/database/logs.txt"; // Replace with your actual file path

	    // Read the content of the file
	    List<String> lines = new ArrayList<>();
	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            lines.add(line);
	        }
	    }

	    // Find and modify the desired entry
	    for (int i = 0; i < lines.size(); i++) {
	        String line = lines.get(i);
	        if (line.contains("Logname:" + lognameToEdit)) {
	            System.out.println("Found logname to edit: " + lognameToEdit);

	            // Extract Logid from the line
	            String logId = extractLogId(line);

	            // Construct the updated line
	            String updatedLine = "Logid:" + logId + ", Logname:" + lognameToEdit + ", Date:" + newDate + ", Status:"
	                    + newStatus + ", Start Time:" + newStartTime + ", End Time:" + newEndTime + ", Category:"
	                    + newCategory + ", LifeCycle:" + newLifeCycleStep + ", ProjectName:" + newProjectName + ", "
	                    + ",Story Points:" + newStoryPoints;

	            // Replace the entire line with the updated line
	            lines.set(i, updatedLine);

	            System.out.println("Updated data: " + updatedLine);

	            break; // Break out of the loop once the entry is modified
	        }
	    }

	    // Write the updated content back to the file
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
	        for (String line : lines) {
	            writer.write(line);
	            writer.newLine();
	        }
	    }
	}

	// Helper method to extract Logid from the line
	private String extractLogId(String line) {
	    String[] parts = line.split(",");
	    for (String part : parts) {
	        if (part.trim().startsWith("Logid:")) {
	            return part.trim().substring(6); // Extract Logid value
	        }
	    }
	    return null; 
	}
}
