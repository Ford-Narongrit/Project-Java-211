package project.neverland;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Scanner;

public class StartProgram extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/loginStage.fxml"));
        primaryStage.setTitle("6210402402");
        primaryStage.setScene(new Scene(root, 960, 600));
        primaryStage.setResizable(false);
        
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}