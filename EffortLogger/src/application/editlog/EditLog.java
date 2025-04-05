package application.editlog;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class EditLog {
	
    public void show(Stage primaryStage) {
    	
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditLog.fxml"));
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
}