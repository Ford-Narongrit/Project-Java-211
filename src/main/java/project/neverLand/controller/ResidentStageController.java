package project.neverLand.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import project.neverLand.models.*;
import project.neverLand.services.CustomDialog;
import project.neverLand.services.ImageSetter;
import project.neverLand.services.StringConfiguration;
import project.neverLand.services.fileDataSource.AccountFileDataSource;
import project.neverLand.services.fileDataSource.ImageDataSource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ResidentStageController {
    private AccountList accountList;
    private Account account;
    private InboxList inboxList;

    private Mail selectedMail;
    private ObservableList inboxObservableList;
    private String imagePath;
    private ImageSetter imageSetter;

    @FXML private AnchorPane residentAnchorPane;

    /** INBOX pane **/
    @FXML private Button inbox;
    @FXML private Pane inboxPane;
    @FXML private TableView<Mail> inboxTable;
    @FXML private ImageView inboxImageView;
    @FXML private Label receiver, sender, size;

    /** profile pane **/
    @FXML private Button profile;
    @FXML private Pane profilePane;
    @FXML private ImageView profileImageView;
    @FXML private Label name, username;
    @FXML private Button changePassword, changeProfile;

    public void setResidentAnchorPane(String path) {
        residentAnchorPane.getStylesheets().add(getClass().getResource(path).toExternalForm());
    }

    @FXML
    public void initialize() {
        imageSetter = new ImageSetter();
        inboxTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedMail(newValue);
            }
        });
    }
    /** inboxPane **/
    public void inboxBtnAction(){
        inboxPane.toFront();
    }
    private void showInboxData(ArrayList<Mail> inboxList) {
        inboxObservableList = FXCollections.observableArrayList(inboxList);
        inboxTable.setItems(inboxObservableList);

        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:Date", "field:date", "width:0.3"));
        configs.add(new StringConfiguration("title:from", "field:senderLocation", "width:0.3"));
        configs.add(new StringConfiguration("title:Worker", "field:workerName", "width:0.4"));

        for (StringConfiguration conf : configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.prefWidthProperty().bind(inboxTable.widthProperty().multiply(Double.parseDouble(conf.get("width"))));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            col.setResizable(false);
            inboxTable.getColumns().add(col);
        }
    }
    private void showSelectedMail(Mail mail) {
        selectedMail = mail;
        imageSetter.setImage(inboxImageView, selectedMail.getImagePath());
        receiver.setText(selectedMail.getReceiver().getFirstName());
        sender.setText(selectedMail.getSender().getFirstName());
        size.setText(String.valueOf(selectedMail.getSize()));
    }

    /** profile **/
    public void profileBtnAction(){
        profilePane.toFront();
    }
    public void reSetPasswordBtnAction() {
        CustomDialog customDialog = new CustomDialog();
        customDialog.setTitleAndHeaderDialog("RePassword", "Please enter new password.");
        customDialog.addButton("Confirm");
        customDialog.createFields();
        customDialog.getResult();
        if (customDialog.isCheckNotnull()) {
            account.setPassword(customDialog.getOutput());
        }
        saveAccountList();
    }
    public void changeProfile(ActionEvent event){
        ImageDataSource imageDataSource = new ImageDataSource();
        imagePath = imageDataSource.getPathForFileChooser(event, "person");
        imageSetter.setImage(profileImageView, imagePath);
        account.setImagePath(imagePath);
        imagePath = "image/profileDefault.jpg";
        saveAccountList();
    }

    /** HOME **/
    public void homeBtnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginStage.fxml"));
        stage.setScene(new Scene(loader.load(),960, 600));
    }

    private void saveAccountList(){
        AccountFileDataSource accountFileDataSource = null;
        try {
            accountFileDataSource = new AccountFileDataSource("data","accountList.csv");
            accountFileDataSource.setAccountList(accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** PassValueToThisStage */
    public void setAccount(Account account) {
        this.account = account;
        name.setText(account.getPersonData().getFirstName());
        username.setText(account.getUsername());
        imageSetter.setImage(profileImageView, account.getImagePath());
    }
    public void setAccountList(AccountList accountList) {
        this.accountList = accountList;
    }
    public void setInboxList(InboxList inboxList) {
        this.inboxList = inboxList;
        showInboxData(inboxList.toPersonList(account.getPersonData()));
    }
}
