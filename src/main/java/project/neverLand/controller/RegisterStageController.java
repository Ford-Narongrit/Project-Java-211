package project.neverLand.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.neverLand.models.*;
import project.neverLand.helper.AlertDefined;


import java.io.IOException;

public class RegisterStageController {
    private AccountList accountList;
    private AddressList addressList;

    @FXML private TextField username, firstname, lastname;
    @FXML private PasswordField password, confirmPassword;
    @FXML private Button create, cancel;
    public void initialize() {
    }

    public void createBtnAction(ActionEvent event) throws IOException {
        if (!isAnyBoxNull()) {
            if (addressList.linkToAddress(new Person(firstname.getText(), lastname.getText()))) {
                if (!accountList.isUsernameDuplicate(username.getText())) {
                    if (isConfirmEqualsPassword()) {
                        Account account = new Account(username.getText(), new Person(firstname.getText(), lastname.getText()), "resident");
                        account.setPassword(password.getText());
                        accountList.addAccount(account);

                        Button b = (Button) event.getSource();
                        Stage stage = (Stage) b.getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginStage.fxml"));
                        stage.setScene(new Scene(loader.load(), 960, 600));
                        LoginStageController loginStageController = loader.getController();
                        loginStageController.setAccountList(accountList);
                    } else {
                        AlertDefined.alertWarning("ConfirmPassword not match", "Please try again.");
                    }
                } else {
                    AlertDefined.alertWarning("account has already use.", "Please try again.");
                }
            } else {
                AlertDefined.alertWarning("Your are not Resident.", "Please register.");
            }
        } else {
            AlertDefined.alertWarning("Any box has null", "Please fill all box.");
        }
    }
    public void cancelBtnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginStage.fxml"));
        stage.setScene(new Scene(loader.load(), 960, 600));
    }

    public boolean isAnyBoxNull() {
        return firstname.getText().equals("") || lastname.getText().equals("") || username.getText().equals("") || password.getText().equals("");
    }
    public boolean isConfirmEqualsPassword() {
        return password.getText().equals(confirmPassword.getText());
    }

    public void setAccountList(AccountList accountList) {
        this.accountList = accountList;
    }
    public void setAddressList(AddressList addressList) {
        this.addressList = addressList;
    }
}
