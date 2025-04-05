package application.pastlogs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PastLogs {

public void show(Stage primaryStage) {
    	
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PastLogs.fxml"));
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
