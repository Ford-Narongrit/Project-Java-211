package project.neverLand.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import project.neverLand.models.Account;
import project.neverLand.models.AccountList;
import project.neverLand.models.Person;
import project.neverLand.helper.AlertDefined;
import project.neverLand.services.CustomDialog;
import project.neverLand.services.StringConfiguration;
import project.neverLand.services.fileDataSource.AccountFileDataSource;

import java.io.IOException;
import java.util.ArrayList;

public class AdminStageController {
    private Account admin;
    private AccountList accountList;
    private Account selectedAccount;

    private ObservableList accountObservableList;
    @FXML private Label name, adminUsername;
    @FXML private Pane registerPane, infoPane, adminPane;
    @FXML private PasswordField password, confirmPassword;
    @FXML private TextField firstName, lastName, username;
    @FXML private Button banBtn, unBanBtn, rePassword, signUp, cancel;
    @FXML private Button registerBtn, homeBtn, manageBtn, adminBtn;
    @FXML private TableView<Account> accountTable;

    @FXML
    public void initialize() {
        banBtn.setVisible(false);
        unBanBtn.setVisible(false);
        accountTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedAccount(newValue);
            }
        });
    }
    /** managePane Function **/
    public void manageBtnAction(){
        infoPane.toFront();
    }
    public void unBanBtnAction() {
        selectedAccount.setBan(false);
        clearSelectedAccount();
        accountTable.refresh();
    }
    public void banBtnAction() {
        selectedAccount.setBan(true);
        clearSelectedAccount();
        accountTable.refresh();
    }
    private void showData() {
        accountObservableList = FXCollections.observableArrayList(accountList.toRoleList("worker"));
        accountTable.setItems(accountObservableList);

        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:Username", "field:username", "width:0.2"));
        configs.add(new StringConfiguration("title:Name", "field:personData", "width:0.5"));
        configs.add(new StringConfiguration("title:BanStage", "field:ban", "width:0.2"));

        for (StringConfiguration conf : configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.prefWidthProperty().bind(accountTable.widthProperty().multiply(Double.parseDouble(conf.get("width"))));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            col.setResizable(false);
            accountTable.getColumns().add(col);
        }
    }
    private void showSelectedAccount(Account account) {
        selectedAccount = account;
        if (!selectedAccount.isBan()) {
            banBtn.setVisible(true);
            unBanBtn.setVisible(false);
        } else {
            unBanBtn.setVisible(true);
            banBtn.setVisible(false);
        }
    }
    private void clearSelectedAccount() {
        selectedAccount = null;
        accountTable.getSelectionModel().clearSelection();
        banBtn.setVisible(false);
        unBanBtn.setVisible(false);
    }

    /** registerPane Function **/
    public void registerBtnAction(){
        registerPane.toFront();
    }
    public void signUpBtnAction() {
        if (!accountList.isUsernameDuplicate(username.getText())) {
            if(checkBoxNull()){
                AlertDefined.alertWarning("failed to sign up","any box id empty");
            }
            else if(!checkConfirmPassword()){
                AlertDefined.alertWarning("failed to sign up","ConfirmPassword not correct");
                password.clear();
                confirmPassword.clear();
            }
            else {
                Account account = new Account(username.getText(), new Person(firstName.getText(), lastName.getText()), "worker");
                account.setPassword(password.getText());
                accountList.addAccount(account);

                try {
                    AccountFileDataSource accountFileDataSource = new AccountFileDataSource("data","accountList.csv");
                    accountFileDataSource.setAccountList(accountList);
                    AlertDefined.alertWarning("create complete", "This username can use now");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                clearAllBox();
                accountTable.getColumns().clear();
                showData();
            }
        }
        else {
            AlertDefined.alertWarning("failed to sign up","This username has already used");
        }
    }
    private boolean checkBoxNull(){
        return (firstName.getText().equals("") || lastName.getText().equals("") || username.getText().equals("") || password.getText().equals(""));
    }
    private boolean checkConfirmPassword() {
        return password.getText().equals(confirmPassword.getText());
    }
    private void clearAllBox(){
        firstName.clear();
        lastName.clear();
        username.clear();
        password.clear();
        confirmPassword.clear();
    }
    public void cancelBtnAction(){
        clearAllBox();
    }

    /** adminPane Function **/
    public void adminBtnAction(){
        adminPane.toFront();
    }
    public void reSetPasswordBtnAction() throws IOException {
        CustomDialog customDialog = new CustomDialog();
        customDialog.setTitleAndHeaderDialog("RePassword", "Please enter new password.");
        customDialog.addButton("Confirm");
        customDialog.createFields();
        customDialog.getResult();
        if (customDialog.isCheckNotnull()) {
            admin.setPassword(customDialog.getOutput());
        }
    }
    public void homeBtnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginStage.fxml"));
        stage.setScene(new Scene(loader.load(), 960, 600));
        LoginStageController loginStageController = loader.getController();
        loginStageController.setAccountList(accountList);
    }

    /** PassValueToThisStage */
    public void setAdmin(Account admin) {
        this.admin = admin;
        name.setText(admin.getPersonData().getFirstName());
        adminUsername.setText(admin.getUsername());
    }
    public void setAccountList(AccountList accountList) {
        this.accountList = accountList;
        showData();
    }

    /** SaveUpdate **/
    
}
