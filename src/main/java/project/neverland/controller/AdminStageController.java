package project.neverland.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import project.neverland.models.Account;
import project.neverland.models.AccountList;
import project.neverland.services.CustomDialog;
import project.neverland.services.StringConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class AdminStageController {
    private Account admin;

    private AccountList accountList;
    private Account selectedAccount;
    private ObservableList peopleObservableList;
    @FXML
    private Label nameWorker;
    @FXML
    private Label usernameWorker;
    @FXML
    private Button banBtn,unBanBtn,repassword;
    @FXML
    private Button registerBtn;
    @FXML
    private TableView<Account> accountTable;

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

    private void showData() {
        peopleObservableList = FXCollections.observableArrayList(accountList.toList());
        accountTable.setItems(peopleObservableList);

        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:Username", "field:username", "width:0.2"));
        configs.add(new StringConfiguration("title:Firstname", "field:firstName", "width:0.3"));
        configs.add(new StringConfiguration("title:Lastname", "field:lastName", "width:0.3"));
        configs.add(new StringConfiguration("title:Banstage", "field:ban", "width:0.2"));

        for (StringConfiguration conf : configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.prefWidthProperty().bind(accountTable.widthProperty().multiply(Double.parseDouble(conf.get("width"))));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            col.setResizable(false);
            accountTable.getColumns().add(col);
        }
    }

    public void showSelectedAccount(Account account){
        selectedAccount = account;
        nameWorker.setText(selectedAccount.getFirstName());
        usernameWorker.setText(selectedAccount.getUsername());
        if (!selectedAccount.isBan()) {
            banBtn.setDisable(false);
            unBanBtn.setDisable(true);
        }
        else {
            banBtn.setDisable(true);
            unBanBtn.setDisable(false);
        }
    }

    public void setAdmin(Account admin) {
        this.admin = admin;
    }

    public void setAccountList(AccountList accountList) {
        this.accountList = accountList;
        showData();
    }

    public void unBanBtnAction(){
        selectedAccount.setBan(false);
        clearSelectedAccount();
        accountTable.refresh();
        accountTable.getSelectionModel().clearSelection();
    }

    public void banBtnAction() {
        selectedAccount.setBan(true);
        clearSelectedAccount();
        accountTable.refresh();
        accountTable.getSelectionModel().clearSelection();
    }

    public void registerBtnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/registerStage.fxml"));
        stage.setScene(new Scene(loader.load(), 960, 600));
        RegisterController registerController = loader.getController();
        registerController.setAccountList(accountList);
        registerController.setAdmin(admin);
    }

    private void clearSelectedAccount() {
        selectedAccount = null;
        banBtn.setDisable(true);
        unBanBtn.setDisable(true);
    }

    @FXML
    private void reSetPassword() {
        CustomDialog customDialog = new CustomDialog();
        customDialog.setTitleAndHeaderDialog("Login","Tessssttttt");
        customDialog.addButton("Confirm");
        customDialog.createFields();
        customDialog.getResult();
        if(customDialog.isCheckNotnull()){
            admin.setPassword(customDialog.getOutput());
//            System.out.println(admin.toString());
        }
    }
}
