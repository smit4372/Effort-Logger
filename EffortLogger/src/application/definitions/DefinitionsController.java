package application.definitions;

import application.dashboard.Dashboard;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;

public class DefinitionsController {
    @FXML private Button dashboardButton;
    @FXML private ChoiceBox<String> typeChoiceBox;
    @FXML private TextField nameTextField;
    @FXML private Button saveButton;
    @FXML private Button deleteButton;

    @FXML
    private void initialize() {
        setupDashboardButton();
        populateTypeChoiceBox();
        setupSaveButton();
        setupDeleteButton();
    }


    private void setupDashboardButton() {
        dashboardButton.setOnAction(event -> {
            Dashboard dashboard = new Dashboard();
            dashboard.show((Stage) dashboardButton.getScene().getWindow());
        });
    }

    private void populateTypeChoiceBox() {
        typeChoiceBox.getItems().addAll("Lifecycle Step", "Project Name");
    }

    private void setupSaveButton() {
        saveButton.setOnAction(event -> {
            saveToDefinitionsFile();
        });
    }

    // TODO: Separate database and controller functionality, and move database functionality to Database class
    private void setupDeleteButton() {
        deleteButton.setOnAction(event -> {
            deleteFromDefinitionsFile();
        });
    }

    
    
    
    private void deleteFromDefinitionsFile() {
        String selectedType = typeChoiceBox.getValue();
        String name = nameTextField.getText().trim();

        String relativePath = "src/database/Definitions.txt";
        File file = new File(relativePath);

        Map<String, Set<String>> typeMap = new HashMap<>();
        boolean isDeleted = false;

        // Read the file and build the map
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    Set<String> names = new HashSet<>(Arrays.asList(parts[1].split(",")));
                    typeMap.put(parts[0].trim(), names);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Check if the name exists and remove it
        if (typeMap.containsKey(selectedType) && typeMap.get(selectedType).remove(name)) {
            isDeleted = true;
        }

        // Write the updated map back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Map.Entry<String, Set<String>> entry : typeMap.entrySet()) {
                writer.write(entry.getKey() + ": " + String.join(",", entry.getValue()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Alert the user
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Deletion Status");
        alert.setHeaderText(null);
        if (isDeleted) {
            alert.setContentText("Deleted Successfully");
        } else {
            alert.setContentText("Item not found");
        }
        alert.showAndWait();
    }

    private void saveToDefinitionsFile() {
        String selectedType = typeChoiceBox.getValue();
        String name = nameTextField.getText();

        String relativePath = "src/database/definitions.txt";
        File file = new File(relativePath);

        // Check if the file exists, and create it if it doesn't
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        
     

        // Read the file and build a map of types and their corresponding names
        Map<String, Set<String>> typeMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    typeMap.computeIfAbsent(parts[0], k -> new HashSet<>()).add(parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Add the new name to the map
        typeMap.computeIfAbsent(selectedType, k -> new HashSet<>()).add(name);

        // Write the updated map back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Map.Entry<String, Set<String>> entry : typeMap.entrySet()) {
                writer.write(entry.getKey() + ": " + String.join(",", entry.getValue()));
                writer.newLine();
            }

            // Show the alert after successful save
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Save Successful");
            alert.setHeaderText(null);
            alert.setContentText("Saved Successfully");
            alert.showAndWait();

        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }
    
    
}
