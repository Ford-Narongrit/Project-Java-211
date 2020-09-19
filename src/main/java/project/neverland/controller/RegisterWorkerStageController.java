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

import java.io.IOException;

public class RegisterWorkerStageController {
    private Account admin;
    private AccountList accountList;
    @FXML
    TextField firstName;
    @FXML
    TextField lastName;
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    PasswordField confirmPassword;
    @FXML
    Button signUp;


    @FXML
    public void initialize() {

    }

    @FXML
    private void signUpBtnAction(ActionEvent event) throws IOException{
        if (!accountList.isUsernameDuplicate(username.getText()) && checkConfirmPassword()) {
            Account account = new Account(username.getText(), firstName.getText(), lastName.getText(), "worker");
            account.setPassword(password.getText());
            accountList.addAccount(account);
            Button b = (Button) event.getSource();
            Stage stage = (Stage) b.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/adminStage.fxml"));
            stage.setScene(new Scene(loader.load(),960, 600));
            AdminStageController adminStageController = loader.getController();
            adminStageController.setAccountList(accountList);
            adminStageController.setAdmin(admin);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("failed to sign up");
            alert.setHeaderText("This username has already used");
            alert.showAndWait();
        }
    }

    private boolean checkConfirmPassword() {
        return password.getText().equals(confirmPassword.getText());
    }

    public void setAccountList(AccountList accountList) {
        this.accountList = accountList;
    }

    public void setAdmin(Account admin) {
        this.admin = admin;
    }

}
