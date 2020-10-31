package project.neverLand.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CreditStageController {
    @FXML
    Button back;

    public void initialize() {
    }

    public void backToHelp(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/helpStage.fxml"));
        stage.setScene(new Scene(loader.load(), 960, 600));
    }
}
