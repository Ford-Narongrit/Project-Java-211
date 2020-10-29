package project.neverLand.controller;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.neverLand.models.AccountList;
import project.neverLand.models.AddressList;
import project.neverLand.models.InboxList;
import project.neverLand.services.fileDataSource.AccountFileDataSource;
import project.neverLand.services.fileDataSource.AddressListFileDataSource;
import project.neverLand.services.fileDataSource.InboxFileDataSource;
import project.neverLand.helper.AlertDefined;
import com.jfoenix.controls.JFXToggleButton;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginStageController {
    @FXML
    private AnchorPane loginAnchorPane;
    @FXML
    private Label register;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginBtn, help;
    @FXML
    private JFXToggleButton toggleButton;

    private AccountList accountList;
    private AddressList addressList;
    private InboxList inboxList;
    private String themeCSSPath;

    AccountFileDataSource accountFileDataSource;

    @FXML
    public void initialize() {
        themeCSSPath = "/css/whiteTheme.css";
        Dotenv dotenv = Dotenv.load();
        String driver = dotenv.get("DRIVER", "file");
        if (driver.equals("file")) {
            try {
                accountFileDataSource = new AccountFileDataSource(
                        dotenv.get("DATA_DIRECTORY", "data"),
                        dotenv.get("ACCOUNT_FILENAME", "accountList.csv"));

                accountList = accountFileDataSource.getAccountList();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                AddressListFileDataSource addressListFileDataSource = new AddressListFileDataSource(
                        dotenv.get("DATA_DIRECTORY", "data"),
                        dotenv.get("ADDRESS_FILENAME", "addressList.csv"));
                addressList = addressListFileDataSource.getAddressList();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            try {
                InboxFileDataSource inboxFileDataSource = new InboxFileDataSource(
                        dotenv.get("DATA_DIRECTORY", "data"),
                        dotenv.get("INBOX_FILENAME", "inboxList.csv"));
                inboxList = inboxFileDataSource.getInboxList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        toggleButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                loginAnchorPane.getStylesheets().clear();
                if (toggleButton.isSelected() == true) {
                    themeCSSPath = "/css/darkTheme.css";
                } else {
                    themeCSSPath = "/css/whiteTheme.css";
                }
                loginAnchorPane.getStylesheets().add(getClass().getResource(themeCSSPath).toExternalForm());
            }
        });
    }

    public void loginBtnAction(ActionEvent event) throws IOException {
        try {
            accountList.login(username.getText(), password.getText());
            Button b = (Button) event.getSource();
            Stage stage = (Stage) b.getScene().getWindow();

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd--HH:mm:ss");
            accountList.getCurrentAccount().setLastLogin(dateFormat.format(new Date()));
            accountFileDataSource.setAccountList(accountList);

            if (accountList.getCurrentAccount().isRole("admin")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/adminStage.fxml"));
                stage.setScene(new Scene(loader.load(), 960, 600));

                AdminStageController adminStageController = loader.getController();
                adminStageController.setAdmin(accountList.getCurrentAccount());
                adminStageController.setAccountList(accountList);
                adminStageController.setAdminAnchorPane(themeCSSPath);
            } else if (accountList.getCurrentAccount().isRole("worker")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/workerStage.fxml"));
                stage.setScene(new Scene(loader.load(), 960, 600));

                WorkerStageController workerStageController = loader.getController();
                workerStageController.setWorker(accountList.getCurrentAccount());
                workerStageController.setAccountList(accountList);
                workerStageController.setAddressList(addressList);
                workerStageController.setInboxList(inboxList);
                workerStageController.setWorkerAnchorPane(themeCSSPath);
            } else if (accountList.getCurrentAccount().isRole("resident")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/residentStage.fxml"));
                stage.setScene(new Scene(loader.load(), 960, 600));

                ResidentStageController residentStageController = loader.getController();
                addressList.linkToAddress(accountList.getCurrentAccount().getPersonData());
                residentStageController.setAccount(accountList.getCurrentAccount());
                residentStageController.setAccountList(accountList);
                residentStageController.setInboxList(inboxList);
                residentStageController.setResidentAnchorPane(themeCSSPath);
            }
        } catch (IllegalAccessException e) {
            AlertDefined.alertWarning(e.getMessage());
            accountFileDataSource.setAccountList(accountList);
        }
    }

    public void registerResidentAction(MouseEvent event) throws IOException {
        Label b = (Label) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/registerStage.fxml"));
        stage.setScene(new Scene(loader.load(), 960, 600));
        RegisterStageController accountResidentController = loader.getController();
        accountResidentController.setAccountList(accountList);
        accountResidentController.setAddressList(addressList);
        accountResidentController.setRegisterAnchorPane(themeCSSPath);
    }

    public void helpBtnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/helpStage.fxml"));
        stage.setScene(new Scene(loader.load(), 960, 600));
    }

    public void setAccountList(AccountList accountList) {
        this.accountList = accountList;
    }

    public void setAddressList(AddressList addressList) {
        this.addressList = addressList;
    }

    public void setInboxList(InboxList inboxList) {
        this.inboxList = inboxList;
    }

}

