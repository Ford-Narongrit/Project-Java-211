package project.neverland.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.neverland.models.AccountList;
import project.neverland.services.DataBase;

import java.io.IOException;

public class LoginController {
    @FXML
    TextField textField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button loginBtn;
    private AccountList accountList;
    private DataBase dataBase;

    @FXML public void initialize(){
        dataBase = new DataBase();
        accountList = dataBase.getPersonData();
    }

    @FXML public void loginBtnAction(ActionEvent event) throws IOException {
//        System.out.println("can run method");
        if(accountList.login(textField.getText(),passwordField.getText())){
            Button b = (Button) event.getSource();
            Stage stage = (Stage) b.getScene().getWindow();
            if(accountList.getCurrentPerson().isRole("admin")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/adminStage.fxml"));
                stage.setScene(new Scene(loader.load(),960, 600));
                AdminStageController adminStageController = loader.getController();
                adminStageController.setAdmin(accountList.getCurrentPerson());
                adminStageController.setAccountList(accountList);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Can not found account");
            alert.setHeaderText("Incorrect Username or Password.");
            alert.showAndWait();
            passwordField.clear();
        }
    }
}

