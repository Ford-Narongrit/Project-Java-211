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

public class LoginStageController {
    @FXML TextField username;
    @FXML PasswordField password;
    @FXML Button loginBtn, registerRtn, help;
    private AccountList accountList;
    private DataBase dataBase;

    @FXML public void initialize(){
        dataBase = new DataBase();
        accountList = dataBase.getPersonData();
    }

    public void loginBtnAction(ActionEvent event) throws IOException {
        if(accountList.login(username.getText(), password.getText())){
            Button b = (Button) event.getSource();
            Stage stage = (Stage) b.getScene().getWindow();
            if(accountList.getCurrentAccount().isBan()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Your account is Ban.");
                alert.setHeaderText("Please try again.");
                alert.showAndWait();
                accountList.getCurrentAccount().banCountAddOne();
                password.clear();
            }
            else if(accountList.getCurrentAccount().isRole("admin")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/adminStage.fxml"));
                stage.setScene(new Scene(loader.load(),960, 600));
                AdminStageController adminStageController = loader.getController();
                adminStageController.setAdmin(accountList.getCurrentAccount());
                adminStageController.setAccountList(accountList);
            }
            else if(accountList.getCurrentAccount().isRole("worker")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/workerStage.fxml"));
                stage.setScene(new Scene(loader.load(),960, 600));
                WorkerStageController workerStageController = loader.getController();
                workerStageController.setWorker(accountList.getCurrentAccount());

            }
            else if(accountList.getCurrentAccount().isRole("resident")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/residentStage.fxml"));
                stage.setScene(new Scene(loader.load(),960, 600));
                RegisterWorkerStageController registerWorkerStageController = loader.getController();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Can not found account");
            alert.setHeaderText("Incorrect Username or Password.");
            alert.showAndWait();
            password.clear();
        }
    }

    public void registerResidentBtnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/registerAccountResidentStage.fxml"));
        stage.setScene(new Scene(loader.load(),960, 600));
        RegisterAccountResidentController accountResidentController = loader.getController();
        accountResidentController.setAccountList(accountList);

    }
    public void helpBtnAction(){
        //load
    }
    public void setAccountList(AccountList accountList) {
        this.accountList = accountList;
    }
}

