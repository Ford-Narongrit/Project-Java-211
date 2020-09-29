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
import project.neverland.models.*;


import java.io.IOException;

public class RegisterAccountResidentController {
    AccountList accountList;
    AddressList addressList;

    @FXML
    TextField username, firstname, lastname;
    @FXML
    PasswordField password, confirmPassword;
    @FXML
    Button create, cancel;

    public void initialize() {
        accountList = new AccountList();
        addressList = new AddressList();
        Address address = new Address("bodin",2,"111/751","singie");
        address.addPersonInRoom(new Person("narongrit","thammapalo"));
        addressList.addAddress(address);

    }

    public void createBtnAction(ActionEvent event) throws IOException {
        if(!isAnyBoxNull()) {
            if (addressList.isPersonInAddress(new Person(firstname.getText(), lastname.getText()))) {
                if (!accountList.isUsernameDuplicate(username.getText())) {
                    if(isConfirmEqualsPassword()){
                        Account account = new Account(username.getText(),new Person(firstname.getText(),lastname.getText()),"resident");
                        account.setPassword(password.getText());
                        accountList.addAccount(account);

                        Button b = (Button) event.getSource();
                        Stage stage = (Stage) b.getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginStage.fxml"));
                        stage.setScene(new Scene(loader.load(),960, 600));
                        LoginStageController loginStageController = loader.getController();
                        loginStageController.setAccountList(accountList);

                    }else{
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("ConfirmPassword not match");
                        alert.setHeaderText("Please try again.");
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("account has already use.");
                    alert.setHeaderText("Please try again.");
                    alert.showAndWait();
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Your are not Resident.");
                alert.setHeaderText("Please register.");
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Any box has null");
            alert.setHeaderText("Please fill all box.");
            alert.showAndWait();
        }
    }

    public void cancelBtnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginStage.fxml"));
        stage.setScene(new Scene(loader.load(),960, 600));
    }

    public boolean isAnyBoxNull(){
        return firstname.getText().equals("") || lastname.getText().equals("") || username.getText().equals("") || password.getText().equals("");
    }

    public boolean isConfirmEqualsPassword(){
        return password.getText().equals(confirmPassword.getText());
    }

    public void setAccountList(AccountList accountList) {
        this.accountList = accountList;
    }
}
