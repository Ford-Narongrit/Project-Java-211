package project.neverland.controller;

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
import project.neverland.models.*;
import project.neverland.models.Package;
import project.neverland.services.AlertDefined;
import project.neverland.services.CustomDialog;
import project.neverland.services.StringConfiguration;

import java.io.IOException;
import java.util.ArrayList;

public class WorkerStageController {
    private InboxList inboxList;
    private AddressList addressList;
    private Account worker;

    private Mail selectedMail;
    private ObservableList mailObservableList;

    @FXML private Pane managePane;
    @FXML private Button manageBtn, registerBtn, profileBtn, homeBtn;
    @FXML private Button addInbox, receivedBtn;
    @FXML private TableView<Mail> inboxTable;
    @FXML private Label receiver, sender, size;
    @FXML private TextField search;

    @FXML private Pane registerPane;
    @FXML private TextField firstname, lastname, building, floor, room, roomType;
    @FXML private Button create, cancel;

    @FXML private Pane profilePane;
    @FXML private Label name, username;
    @FXML private Button rePassword;

    @FXML private Pane addNewInboxPane;
    @FXML private TextField receiverFirstname, receiverLastname, receiverAddress;
    @FXML private TextField senderFirstname, senderLastname, senderAddress;
    @FXML private TextField sizeAddInbox, degree, station, trackingNum;
    @FXML private Button addNewInbox, Cancel;


    @FXML public void initialize(){
        inboxTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedMail(newValue);
            }
        });
    }
    public void manageBtnAction(){
        managePane.toFront();
    }
    public void registerBtnAction(){
        registerPane.toFront();
    }
    public void profileBtnAction(){
        profilePane.toFront();
    }
    public void addInboxBtnAction(){
        addNewInboxPane.toFront();
    }
    public void homeBtnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginStage.fxml"));
        stage.setScene(new Scene(loader.load(), 960, 600));
    }
    public void reSetPasswordBtnAction() throws IOException {
        CustomDialog customDialog = new CustomDialog();
        customDialog.setTitleAndHeaderDialog("Repassword", "Please enter new password.");
        customDialog.addButton("Confirm");
        customDialog.createFields();
        customDialog.getResult();
        if (customDialog.isCheckNotnull()) {
            worker.setPassword(customDialog.getOutput());
            System.out.println(worker.toString());
        }
    }
    public void cancelBtnAction(){
        clearAllField();
        managePane.toFront();
    }
    public void createBtnAction(){
        Address address = new Address(building.getText(),floor.getText(),room.getText(),roomType.getText());
        address.addPersonToRoom(new Person(firstname.getText(),lastname.getText()));
        addressList.addAddress(address);
        clearAllField();
    }


    public void addNewInboxBtnAction(){
        if(!checkAllBoxNull()) {
            Mail mail;
            if (isPackage()) {
                mail = new Package(new Person(senderFirstname.getText(), senderLastname.getText()),
                        senderAddress.getText(),
                        new Person(receiverFirstname.getText(), receiverLastname.getText()),
                        receiverAddress.getText(),
                        station.getText(), trackingNum.getText());
            } else if (isDocument()) {
                mail = new Document(new Person(senderFirstname.getText(), senderLastname.getText()),
                        senderAddress.getText(),
                        new Person(receiverFirstname.getText(), receiverLastname.getText()),
                        receiverAddress.getText(),
                        degree.getText());
            } else {
                mail = new Mail(new Person(senderFirstname.getText(), senderLastname.getText()),
                        senderAddress.getText(),
                        new Person(receiverFirstname.getText(), receiverLastname.getText()),
                        receiverAddress.getText());
            }
            inboxList.addInbox(mail);
            inboxTable.getColumns().clear();
            showData();
            managePane.toFront();

        }
        else{
            AlertDefined.alertWarning("failed to addInbox","please input all box");
        }
    }

    private boolean checkAllBoxNull(){
        return  senderFirstname.getText().equals("") ||
                senderLastname.getText().equals("") ||
                senderAddress.getText().equals("") ||
                receiverFirstname.getText().equals("") ||
                receiverLastname.getText().equals("") ||
                receiverAddress.getText().equals("") ||
                size.getText().equals("");
    }
    private boolean isPackage(){
        return !station.getText().equals("") && !trackingNum.getText().equals("");
    }
    private boolean isDocument(){
        return !degree.getText().equals("");
    }

    public void receivedInboxBtnAction(){
        selectedMail.setReceived(true);
        clearSelectedMail();
        inboxTable.getColumns().clear();
        showData();
    }

    private void showSelectedMail(Mail mail) {
        selectedMail = mail;
        receiver.setText(selectedMail.getReceiver().getFirstName());
        sender.setText(selectedMail.getSender().getFirstName());
        size.setText(String.valueOf(selectedMail.getSize()));
    }

    private void showData() {
        mailObservableList = FXCollections.observableArrayList(inboxList.toNotReceivedList());
        inboxTable.setItems(mailObservableList);

        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:Receiver", "field:receiver", "width:0.2"));
        configs.add(new StringConfiguration("title:Sender", "field:sender", "width:0.3"));
        configs.add(new StringConfiguration("title:size", "field:size", "width:0.3"));

        for (StringConfiguration conf : configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.prefWidthProperty().bind(inboxTable.widthProperty().multiply(Double.parseDouble(conf.get("width"))));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            col.setResizable(false);
            inboxTable.getColumns().add(col);
        }
    }

    private void clearSelectedMail(){
        selectedMail = null;
        inboxTable.getSelectionModel().clearSelection();
    }
    private void clearAllField(){
        firstname.clear();
        lastname.clear();
        building.clear();
        floor.clear();
        room.clear();
        roomType.clear();
        receiverFirstname.clear();
        receiverLastname.clear();
        receiverAddress.clear();
        senderFirstname.clear();
        senderLastname.clear();
        senderAddress.clear();
        sizeAddInbox.clear();
        degree.clear();
        station.clear();
        trackingNum.clear();
    }

    public void setWorker(Account worker) {
        this.worker = worker;
        name.setText(worker.getPersonData().getFirstName());
        username.setText(worker.getUsername());
    }
    public void setInboxList(InboxList inboxList) {
        this.inboxList = inboxList;
        showData();
    }
    public void setAddressList(AddressList addressList) {
        this.addressList = addressList;

    }
}
