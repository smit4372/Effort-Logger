package application.newlog;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class NewLog {
	
	public void show(Stage primaryStage) {
		
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewLog.fxml"));
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
