package project.neverland.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import project.neverland.models.AccountList;
import project.neverland.models.AddressList;
import project.neverland.models.InboxList;
import project.neverland.models.Mail;
import project.neverland.services.AddressDataBase;
import project.neverland.services.AlertDefined;
import project.neverland.services.AccountDataBase;
import project.neverland.services.InboxDataBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class LoginStageController {
    @FXML Label register;
    @FXML TextField username;
    @FXML PasswordField password;
    @FXML Button loginBtn, help;

    private AccountDataBase accountDataBase;
    private AccountList accountList;

    private AddressDataBase addressDataBase;
    private AddressList addressList;

    private InboxDataBase inboxDataBase;
    private InboxList inboxList;
    @FXML public void initialize(){
        accountDataBase = new AccountDataBase();
        accountList = accountDataBase.getPersonData();

        addressDataBase = new AddressDataBase();
        addressList = addressDataBase.getAddressList();

        inboxDataBase = new InboxDataBase();
        inboxList = inboxDataBase.getInboxData();

        System.out.println(inboxList.toList());
    }

    public void loginBtnAction(ActionEvent event) throws IOException {
        if(accountList.login(username.getText(), password.getText())){
            Button b = (Button) event.getSource();
            Stage stage = (Stage) b.getScene().getWindow();
            if(accountList.getCurrentAccount().isBan()){
                AlertDefined.alertWarning("Your account is Ban.", "Please try again.");
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
                workerStageController.setAddressList(addressList);
                workerStageController.setInboxList(inboxList);

            }
            else if(accountList.getCurrentAccount().isRole("resident")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/residentStage.fxml"));
                stage.setScene(new Scene(loader.load(),960, 600));

                ResidentStageController residentStageController = loader.getController();
                addressList.linkToAddress(accountList.getCurrentAccount().getPersonData());
                residentStageController.setAddress(addressList.getCurrentAddress());
                residentStageController.setAccount(accountList.getCurrentAccount());
                residentStageController.setInboxList(inboxList);

            }
        }
        else{
            AlertDefined.alertWarning("Can not found account.", "Incorrect Username or Password.");
            password.clear();
        }
    }

    public void registerResidentAction(MouseEvent event) throws IOException {
        Label b = (Label) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/registerStage.fxml"));
        stage.setScene(new Scene(loader.load(),960, 600));
        RegisterStageController accountResidentController = loader.getController();
        accountResidentController.setAccountList(accountList);
    }
    public void helpBtnAction(){
        //load
    }
    public void setAccountList(AccountList accountList) {
        this.accountList = accountList;
    }
}

