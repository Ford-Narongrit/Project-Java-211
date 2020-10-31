package project.neverLand.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import project.neverLand.models.Account;
import project.neverLand.models.AccountList;
import project.neverLand.models.Person;
import project.neverLand.helper.AlertDefined;
import project.neverLand.services.CustomDialog;
import project.neverLand.services.ImageSetter;
import project.neverLand.services.StringConfiguration;
import project.neverLand.services.fileDataSource.AccountFileDataSource;
import project.neverLand.services.fileDataSource.ImageDataSource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AdminStageController {
    private Account admin;
    private AccountList accountList;
    private Account selectedAccount;

    private ObservableList accountObservableList;
    private String imagePath;
    private ImageDataSource imageDateSource;
    private ImageSetter imageSetter;

    @FXML
    private AnchorPane adminAnchorPane;

    @FXML
    private Pane managePane;
    @FXML
    private Button banBtn, unBanBtn;
    @FXML
    private TableView<Account> accountTable;
    @FXML
    private ImageView manageImageView;

    @FXML
    private Pane registerPane;
    @FXML
    private TextField firstName, lastName, username;
    @FXML
    private PasswordField password, confirmPassword;
    @FXML
    private ImageView registerImageView;
    @FXML
    private Button signUpBtn, cancelBtn, chooseImageBtn;

    @FXML
    private Pane adminPane;
    @FXML
    private ImageView adminImage;
    @FXML
    private Label name, adminUsername;
    @FXML
    private Button changeProfileBtn, changePasswordBtn;

    public void setAdminAnchorPane(String path) {
        adminAnchorPane.getStylesheets().add(getClass().getResource(path).toExternalForm());
    }

    @FXML
    public void initialize() {
        imageSetter = new ImageSetter();
        imageDateSource = new ImageDataSource();
        imagePath = "image/profileDefault.jpg";

        banBtn.setVisible(false);
        unBanBtn.setVisible(false);
        accountTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedAccount(newValue);
            }
        });
    }

    /**
     * managePane Function
     **/
    public void manageBtnAction() {
        managePane.toFront();
    }

    public void unBanBtnAction() {
        selectedAccount.setBan(false);
        clearSelectedAccount();
        accountTable.refresh();
        save();
    }

    public void banBtnAction() {
        selectedAccount.setBan(true);
        clearSelectedAccount();
        accountTable.refresh();
        save();
    }

    private void showData() {
        accountObservableList = FXCollections.observableArrayList(accountList.toRoleList("worker"));
        accountTable.setItems(accountObservableList);

        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:Last login", "field:lastLogin", "width:0.3"));
        configs.add(new StringConfiguration("title:Username", "field:username", "width:0.3"));
        configs.add(new StringConfiguration("title:Name", "field:personData", "width:0.2"));
        configs.add(new StringConfiguration("title:Ban login count", "field:loginBanCount", "width:0.2"));

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
        imageSetter.setImage(manageImageView, selectedAccount.getImagePath());
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
        imageSetter.setImage(manageImageView, imagePath);
        accountTable.getSelectionModel().clearSelection();
        banBtn.setVisible(false);
        unBanBtn.setVisible(false);
    }

    /**
     * registerPane Function
     **/
    public void registerBtnAction() {
        registerPane.toFront();
    }

    public void signUpBtnAction() {
        try {
            accountList.register("worker", firstName.getText(), lastName.getText(), username.getText(), password.getText(), confirmPassword.getText(), imagePath, null);
            save();
            AlertDefined.alertNormal("Register successful.");
            accountTable.getColumns().clear();
            showData();
        } catch (IllegalAccessException e) {
            AlertDefined.alertWarning(e.getMessage());
        }
        clearAllBox();
    }

    private void clearAllBox() {
        firstName.clear();
        lastName.clear();
        username.clear();
        password.clear();
        confirmPassword.clear();
        imagePath = "image/profileDefault.jpg";
        imageSetter.setImage(registerImageView, imagePath);
    }

    public void cancelBtnAction() {
        clearAllBox();
    }

    public void chooseImageBtnAction(ActionEvent event) {
        imagePath = imageDateSource.getPathForFileChooser(event, "person");
        imageSetter.setImage(registerImageView, imagePath);
    }

    /**
     * adminPane Function
     **/
    public void adminBtnAction() {
        adminPane.toFront();
    }

    public void reSetPasswordBtnAction() {
        CustomDialog customDialog = new CustomDialog();
        customDialog.setTitleAndHeaderDialog("RePassword", "Please enter new password.");
        customDialog.addButton("Confirm");
        customDialog.createFields();
        customDialog.getResult();
        if (customDialog.isCheckNotnull()) {
            admin.setPassword(customDialog.getOutput());
        }
        save();
    }

    public void changeProfile(ActionEvent event) {
        admin.setImagePath(imageDateSource.getPathForFileChooser(event, "person"));
        imageSetter.setImage(adminImage, admin.getImagePath());
        save();
    }

    /**
     * HOME
     **/
    public void homeBtnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginStage.fxml"));
        stage.setScene(new Scene(loader.load(), 960, 600));
        LoginStageController loginStageController = loader.getController();
        loginStageController.setAccountList(accountList);
    }

    /**
     * SaveUpdate
     **/
    private void save() {
        AccountFileDataSource accountFileDataSource = null;
        try {
            accountFileDataSource = new AccountFileDataSource("data", "accountList.csv");
            accountFileDataSource.setAccountList(accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * PassValueToThisStage
     */
    public void setAdmin(Account admin) {
        this.admin = admin;
        name.setText(admin.getPersonData().toString());
        adminUsername.setText(admin.getUsername());
        imageSetter.setImage(adminImage, admin.getImagePath());
    }

    public void setAccountList(AccountList accountList) {
        this.accountList = accountList;
        showData();
    }
}
