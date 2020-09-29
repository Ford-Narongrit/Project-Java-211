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
import project.neverland.models.Account;
import project.neverland.models.AccountList;
import project.neverland.models.Person;

import java.io.IOException;

public class RegisterWorkerStageController {
    private Account admin;
    private AccountList accountList;
    @FXML
    TextField firstName, lastName, username;
    @FXML
    PasswordField password, confirmPassword;
    @FXML
    Button signUp, cancel;


    @FXML
    public void initialize() {
    }

    public void signUpBtnAction(ActionEvent event) throws IOException{
        if (!accountList.isUsernameDuplicate(username.getText())) {
            if(checkBoxNull()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("failed to sign up");
                alert.setHeaderText("any box id empty");
                alert.showAndWait();
            }
            else if(!checkConfirmPassword()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("failed to sign up");
                alert.setHeaderText("ConfirmPassword not correct");
                alert.showAndWait();
            }
            else {
                Account account = new Account(username.getText(), new Person(firstName.getText(), lastName.getText()), "worker");
                account.setPassword(password.getText());
                accountList.addAccount(account);
                Button b = (Button) event.getSource();
                Stage stage = (Stage) b.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/adminStage.fxml"));
                stage.setScene(new Scene(loader.load(), 960, 600));
                AdminStageController adminStageController = loader.getController();
                adminStageController.setAccountList(accountList);
                adminStageController.setAdmin(admin);
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("failed to sign up");
            alert.setHeaderText("This username has already used");
            alert.showAndWait();
        }
    }
    public void cancelBtnAction(ActionEvent event) throws IOException{
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/adminStage.fxml"));
        stage.setScene(new Scene(loader.load(), 960, 600));
        AdminStageController adminStageController = loader.getController();
        adminStageController.setAccountList(accountList);
        adminStageController.setAdmin(admin);
    }

    private boolean checkConfirmPassword() {
        return password.getText().equals(confirmPassword.getText());
    }
    private boolean checkBoxNull(){
        return (firstName.getText().equals("") || lastName.getText().equals("") || username.getText().equals("") || password.getText().equals(""));
    }

    public void setAccountList(AccountList accountList) {
        this.accountList = accountList;
    }

    public void setAdmin(Account admin) {
        this.admin = admin;
    }

}
