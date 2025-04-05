package application;

import application.login.Login;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Login login = new Login();
        login.show(primaryStage);
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
