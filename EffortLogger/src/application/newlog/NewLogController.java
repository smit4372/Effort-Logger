package application.newlog;

import application.dashboard.Dashboard;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class NewLogController<Strings> implements Initializable{

    @FXML
    private Button addLog;

    @FXML
    private ChoiceBox<Strings> category;
  
    @FXML
    private ChoiceBox<Strings> lifeCycleStep;

    @FXML
    private Button dashboardButton;

    @FXML
    private DatePicker date;

    @FXML
    private TextField endTime;

    @FXML
    private Button estimate;

    @FXML
    private TextField logName;

    @FXML
    private AnchorPane pane;

    @FXML
    private ChoiceBox<Strings> projectName;
    
    @FXML
    private TextField startTime;

    @FXML
    private ChoiceBox<Strings> status;

    @FXML
    private TextField storyPoints;

    @FXML
    void onClickEvent(MouseEvent event) {
    	//System.out.println("hello world");
    }
    
    public static String generateHexId() {
        Random random = new Random();
        StringBuilder hexId = new StringBuilder();

        // Generate 6 random hex digits
        for (int i = 0; i < 6; i++) {
            int randomDigit = random.nextInt(16); // 0 to 15
            hexId.append(Integer.toHexString(randomDigit));
        }

        return hexId.toString().toUpperCase(); // Convert to uppercase for consistency
    }
   
    public static int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(5); // Generates a random number between 0 (inclusive) and 5
    }
    
  	@SuppressWarnings("unchecked")
	@Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
  		setupDashboardButton();
   	
    	status.getItems().add((Strings) "In Progress");
    	status.getItems().add((Strings) "Completed");
    	//status.getItems().add((Strings) "Test1");
    	//status.getItems().add((Strings) "Test1");
    	
    	status.valueProperty().addListener((observable, oldValue, newValue) -> {
            boolean isInProgress = "In Progress".equals(newValue);
            endTime.setDisable(isInProgress);
        });
    	

        // Read data from Definitions.txt and populate projectName and lifeCycleStep ChoiceBox
        readDefinitionsFile();
        
   // 	projectName.getItems().add((Strings) "Project 1");
    //	lifeCycleStep.getItems().add((Strings) "Project 2");
    	
    	category.getItems().add((Strings) "Effort");
    	category.getItems().add((Strings) "Defect");
  /*  	category.getItems().add((Strings) "Project 4");
    	category.getItems().add((Strings) "Project 5");
    	category.getItems().add((Strings) "Project 6");
    */
    	
        estimate.setOnAction(event -> {
            //System.out.println("Hello, World!");
        	int randomNumber = generateRandomNumber();
        	storyPoints.setText(String.valueOf(randomNumber));
        });
        
        
  	}
  	
	private void setupDashboardButton() {
    	dashboardButton.setOnAction(event -> {
            Dashboard dashboard = new Dashboard();
            dashboard.show((Stage) dashboardButton.getScene().getWindow());
        });
    }

////Users/pranavzagade/Downloads/EffortLogger/EffortLogger/
  	
  	 @SuppressWarnings("unchecked")
  	private void readDefinitionsFile() {
         Path filePath = Paths.get("src/database/Definitions.txt");

         try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
             String line;
             List<String> projectNames = new ArrayList<>();
             List<String> lifeCycleSteps = new ArrayList<>();

             while ((line = reader.readLine()) != null) {
                 if (line.startsWith("Project Name:")) {
                     String[] names = line.substring("Project Name:".length()).split(",");
                     projectNames.addAll(Arrays.asList(names));
                 } else if (line.startsWith("Lifecycle Step:")) {
                     String step = line.substring("Lifecycle Step:".length());
                     lifeCycleSteps.add(step);
                 }
             }

             projectName.setItems(FXCollections.observableArrayList((Strings[]) projectNames.toArray()));
             lifeCycleStep.setItems(FXCollections.observableArrayList((Strings[]) lifeCycleSteps.toArray()));

         } catch (IOException e) {
             e.printStackTrace();
             System.err.println("Error reading from Definitions.txt");
         }
     }


    @FXML
    private void onClick() {
        // Handle the button click event
        String logNameText = logName.getText();
        LocalDate selectedDate = date.getValue();
        String dateText = (selectedDate != null) ? selectedDate.toString() : "No Date Selected";
        String statusText = (String) status.getValue();
        String startTimeText = startTime.getText();
        String endTimeText = endTime.getText();
        String lifeCycleStepText = (String) lifeCycleStep.getValue();
        String projectNameText = (String) projectName.getValue();
        String categoryText = (String) category.getValue();
        String storyPointsText = storyPoints.getText();

        Path filePath = Paths.get("Logs.txt");
        System.out.println("Absolute Path: " + filePath.toAbsolutePath());


        // You can now use these values as needed (e.g., print them to the console)
        System.out.println(generateHexId());
        System.out.println("Log Name: " + logNameText);
        System.out.println("Date: " + dateText);
        System.out.println("Status: " + statusText);
        System.out.println("Start Time: " + startTimeText);
        System.out.println("End Time: " + endTimeText);
        System.out.println("Life Cycle Step: " + lifeCycleStepText);
        System.out.println("Project Name: " + projectNameText);
        System.out.println("Category: " + categoryText);
        System.out.println("Story Points: " + storyPointsText);
        
        String logEntry = String.format(
                "Logid: %s, Logname:%s, Date:%s, Status:%s, Start Time:%s, End Time:%s, Lifecycle Step:%s, Project Name:%s, Category:%s, Story Points:%s%n",
                generateHexId(), logNameText, dateText, statusText, startTimeText, endTimeText, lifeCycleStepText, projectNameText, categoryText, storyPointsText);
        
        try {
            Path filePath1 = Paths.get("src/database/logs.txt");
            FileWriter fileWriter = new FileWriter(filePath1.toFile(), true); // Append mode
            fileWriter.write(logEntry);
            fileWriter.close();
            System.out.println("Log entry written to Logs.txt");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing to Logs.txt");
        }

        
    }
    
}
