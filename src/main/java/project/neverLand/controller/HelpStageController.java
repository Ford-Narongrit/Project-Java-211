package project.neverLand.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelpStageController {
    @FXML
    Button credit, back;
    @FXML
    VBox srcollBoard;

    @FXML
    public void initialize() {
        loadManual();
    }

    private void loadManual() {
        for (int i = 1; i <= 15; i++) {
            ImageView imageView = new ImageView();
            imageView.setImage(new Image("image/help/help" + i + ".jpg", 905, 1265, false, false));
            srcollBoard.getChildren().add(imageView);
        }
    }

    public void creditBtnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/creditStage.fxml"));
        stage.setScene(new Scene(loader.load(), 960, 600));
    }

    public void backBtnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginStage.fxml"));
        stage.setScene(new Scene(loader.load(), 960, 600));
    }
}
