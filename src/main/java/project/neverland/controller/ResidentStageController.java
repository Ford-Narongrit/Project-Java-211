package project.neverland.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ResidentStageController {
    @FXML Button inbox, profile, home;
    @FXML TableView inboxTable;
    @FXML Button changePassword;
    @FXML Pane inboxPane, profilePane;
    @FXML
    public void initialize() {

    }

    public void inboxBtnAction(){
        inboxPane.toFront();
    }
    public void profileBtnAction(){
        profilePane.toFront();
    }

    public void homeBtnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginStage.fxml"));
        stage.setScene(new Scene(loader.load(),960, 600));
    }
}
