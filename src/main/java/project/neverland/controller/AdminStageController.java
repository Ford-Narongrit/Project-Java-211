package project.neverland.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import project.neverland.models.Account;
import project.neverland.models.AccountList;
import project.neverland.services.CustomDialog;
import project.neverland.services.StringConfiguration;

import java.io.IOException;
import java.util.ArrayList;

public class AdminStageController {
    private Account admin;

    private AccountList accountList;
    private Account selectedAccount;
    private ObservableList accountObservableList;
    @FXML private Label nameWorker;
    @FXML private Label usernameWorker;
    @FXML private Button banBtn, unBanBtn, rePassword;
    @FXML private Button registerBtn, home;
    @FXML private TableView<Account> accountTable;

    @FXML
    public void initialize() {
        banBtn.setDisable(true);
        unBanBtn.setDisable(true);
        accountTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedAccount(newValue);
            }
        });
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

    public void registerBtnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/registerWorkerStage.fxml"));
        stage.setScene(new Scene(loader.load(), 960, 600));
        RegisterWorkerStageController registerWorkerStageController = loader.getController();
        registerWorkerStageController.setAccountList(accountList);
        registerWorkerStageController.setAdmin(admin);
    }

    public void reSetPasswordBtnAction() throws IOException {
        CustomDialog customDialog = new CustomDialog();
        customDialog.setTitleAndHeaderDialog("Repassword", "Please enter new password.");
        customDialog.addButton("Confirm");
        customDialog.createFields();
        customDialog.getResult();
        if (customDialog.isCheckNotnull()) {
            admin.setPassword(customDialog.getOutput());
            System.out.println(admin.toString());
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
        nameWorker.setText(selectedAccount.getPersonData().getFirstName());
        usernameWorker.setText(selectedAccount.getUsername());
        if (!selectedAccount.isBan()) {
            banBtn.setDisable(false);
            unBanBtn.setDisable(true);
        } else {
            banBtn.setDisable(true);
            unBanBtn.setDisable(false);
        }
    }
    private void clearSelectedAccount() {
        selectedAccount = null;
        accountTable.getSelectionModel().clearSelection();
        banBtn.setDisable(true);
        unBanBtn.setDisable(true);
    }

    public void setAdmin(Account admin) {
        this.admin = admin;
    }

    public void setAccountList(AccountList accountList) {
        this.accountList = accountList;
        showData();
    }
}
