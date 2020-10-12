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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.neverLand.helper.AlertDefined;
import project.neverLand.models.*;
import project.neverLand.models.Package;
import project.neverLand.services.CustomDialog;
import project.neverLand.services.StringConfiguration;
import project.neverLand.services.fileDataSource.AccountFileDataSource;
import project.neverLand.services.fileDataSource.AddressListFileDataSource;
import project.neverLand.services.fileDataSource.ImageDataSource;
import project.neverLand.services.fileDataSource.InboxFileDataSource;

import java.io.IOException;
import java.util.ArrayList;

public class WorkerStageController {
    private AccountList accountList;
    private InboxList inboxList;
    private AddressList addressList;
    private Account worker;

    private ImageDataSource imageDateSource;
    private String personImagePath;
    private String inboxImagePath;

    /** infoPane **/
    @FXML private Pane infoPane;
    @FXML private Button infoBtn;
    @FXML private TableView<Address> addressTable;
    private ObservableList addressObservableList;
    private Address selectedAddress;
    @FXML private VBox personBox;

    /** managePane **/
    @FXML private Pane managePane;
    @FXML private Button manageBtn;
    @FXML private TableView<Mail> inboxTable;
    private ObservableList inboxObservableList;
    private Mail selectedMail;
    @FXML private Label receiver, sender, size;
    @FXML private TextField search;
    @FXML private ImageView inboxImageView;

    /** addNewInboxPane **/
    @FXML private Pane addNewInboxPane;
    @FXML private TextField receiverFirstname, receiverLastname;
    @FXML private TextArea receiverAddress;
    @FXML private TextField senderFirstname, senderLastname;
    @FXML private TextArea senderAddress;
    @FXML private TextField width, length, height, degree, station, trackingNum;
    @FXML private ImageView newInboxImageView;
    @FXML private Button addNewInboxBtn, addNewInboxCancelBtn, chooseImageBtn;

    /** registerPane **/
    @FXML private Pane registerPane;
    @FXML private TextField firstname, lastname;
    @FXML private ComboBox building, floor, room, roomType;
    @FXML private Button registerBtn, createBtn, registerCancelBtn, registerChooseImageBtn;
    @FXML private ImageView registerImageView;

    /** profilePane **/
    @FXML private Pane profilePane;
    @FXML private Label name, username;
    @FXML private Button profileBtn, rePassword, changeProfile;
    @FXML private ImageView profileImageView;

    @FXML public void initialize(){
        imageDateSource = new ImageDataSource();
        setTextComboBox();
        addressTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedAddress(newValue);
            }
        });

        inboxTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedMail(newValue);
            }
        });
    }

    /** infoPane **/
    public void infoBtnAction(){
        infoPane.toFront();
    }
    private void showAddressData(){
        addressObservableList = FXCollections.observableArrayList(addressList.toList());
        addressTable.setItems(addressObservableList);

        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:Room Number", "field:roomNumber", "width:0.1"));
        configs.add(new StringConfiguration("title:Floor", "field:floor", "width:0.2"));
        configs.add(new StringConfiguration("title:Building", "field:building", "width:0.3"));
        configs.add(new StringConfiguration("title:Room Type", "field:roomType", "width:0.4"));

        for(StringConfiguration configuration : configs){
            TableColumn col = new TableColumn(configuration.get("title"));
            col.prefWidthProperty().bind(addressTable.widthProperty().multiply(Double.parseDouble(configuration.get("width"))));
            col.setCellValueFactory(new PropertyValueFactory<>(configuration.get("field")));
            col.setResizable(false);
            addressTable.getColumns().add(col);
        }
    }
    private void showSelectedAddress(Address address){
        personBox.getChildren().clear();
        selectedAddress = address;
        for(Person person: selectedAddress.getRoomers()){
            ImageView imageView = new ImageView(new Image(person.getImagePath()));
            //todo set width height image
            personBox.getChildren().add(imageView);
            Label personName = new Label(person.toString());
            //todo set css to label personName
            personBox.getChildren().add(personName);
        }
    }
    //todo create method can edit personData

    /** managePane **/
    public void manageBtnAction(){
        managePane.toFront();
    }
    private void showInboxData() {
        inboxObservableList = FXCollections.observableArrayList(inboxList.toNotReceivedList());
        inboxTable.setItems(inboxObservableList);

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
    private void showSelectedMail(Mail mail){
        selectedMail = mail;
        inboxImageView.setImage(new Image(selectedMail.getImagePath()));
        receiver.setText(selectedMail.getReceiver().getFirstName());
        sender.setText(selectedMail.getSender().getFirstName());
        size.setText(String.valueOf(selectedMail.getSize()));
    }
    public void receivedInboxBtnAction(){
        selectedMail.setReceived(true);
        saveUpdateInboxList();
        clearSelectMail();
        inboxTable.getColumns().clear();
        showInboxData();
    }

    /** addNewInboxPane **/
    public void addInboxBtnAction(){
        addNewInboxPane.toFront();
    }
    public void addNewInboxChooseImage(ActionEvent event){
        inboxImagePath = imageDateSource.getPathForFileChooser(event);
        newInboxImageView.setImage(new Image(inboxImagePath));
    }
    public void addNewInboxBtnAction(){
        if(!checkAllBoxNull()) {
            Mail mail;
            if (isPackage()) {
                mail = new Package(new Person(senderFirstname.getText(), senderLastname.getText()),
                        senderAddress.getText(),
                        new Person(receiverFirstname.getText(), receiverLastname.getText()),
                        receiverAddress.getText(),
                        station.getText(), trackingNum.getText(), inboxImagePath,
                        Double.parseDouble(width.getText()), Double.parseDouble(length.getText()),
                        Double.parseDouble(height.getText()));
            } else if (isDocument()) {
                mail = new Document(new Person(senderFirstname.getText(), senderLastname.getText()),
                        senderAddress.getText(),
                        new Person(receiverFirstname.getText(), receiverLastname.getText()),
                        receiverAddress.getText(),
                        degree.getText(),
                        inboxImagePath, Double.parseDouble(width.getText()), Double.parseDouble(length.getText()));
            } else {
                mail = new Mail(new Person(senderFirstname.getText(), senderLastname.getText()),
                        senderAddress.getText(),
                        new Person(receiverFirstname.getText(), receiverLastname.getText()),
                        receiverAddress.getText(),
                        inboxImagePath, Double.parseDouble(width.getText()), Double.parseDouble(length.getText()));
            }
            mail.calSize();
            inboxList.addInbox(mail);
            saveUpdateInboxList();
            clearAddInboxField();
            inboxTable.getColumns().clear();
            showInboxData();
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
    public void addNewInboxCancelBtnAction(){
        managePane.toFront();
        clearAddInboxField();
    }

    /** registerPane **/
    public void registerBtnAction(){
        registerPane.toFront();
    }
    private void setTextComboBox(){
        building.getItems().addAll("1","2");
        floor.getItems().addAll("1","2","3","4","5","6","7","8");
        roomType.getItems().addAll("one bedroom","two bedrooms");
        room.getItems().addAll("1","2","3","4","5","6","7","8","9","10");
    }
    public void registerChooseImage(ActionEvent event){
        personImagePath = imageDateSource.getPathForFileChooser(event);
        registerImageView.setImage(new Image(personImagePath));
    }
    public void createBtnAction(){
        addressList.findAddress((String)building.getValue(),(String)floor.getValue(),(String)room.getValue(),(String)roomType.getValue());
        addressList.getCurrentAddress().addPersonToRoom(new Person(firstname.getText(),lastname.getText(),personImagePath));
        saveUpdateAddressList();
        clearRegisterPaneField();
    }
    public void registerCancelBtnAction(){
        clearRegisterPaneField();
    }

    /** profilePane **/
    public void profileBtnAction(){
        profilePane.toFront();
    }
    public void reSetPasswordBtnAction() throws IOException {
        CustomDialog customDialog = new CustomDialog();
        customDialog.setTitleAndHeaderDialog("RePassword", "Please enter new password.");
        customDialog.addButton("Confirm");
        customDialog.createFields();
        customDialog.getResult();
        if (customDialog.isCheckNotnull()) {
            worker.setPassword(customDialog.getOutput());
            System.out.println(worker.toString());
        }
        saveUpdateAccountList();
    }
    public void changeProfile(ActionEvent event){
        personImagePath = imageDateSource.getPathForFileChooser(event);
        profileImageView.setImage(new Image(personImagePath));
        worker.setImagePath(personImagePath);
        personImagePath = "image/profileDefault.jpg";
        saveUpdateAccountList();
    }

    /** home **/
    public void homeBtnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginStage.fxml"));
        stage.setScene(new Scene(loader.load(), 960, 600));
        LoginStageController loginStageController = loader.getController();
        loginStageController.setInboxList(inboxList);
        loginStageController.setAddressList(addressList);
    };

    /** clear/save/update **/
    private void clearSelectMail(){
        inboxImagePath = "image/emptyInbox.png";
        inboxImageView.setImage(new Image(inboxImagePath));
        selectedMail = null;
        inboxTable.getSelectionModel().clearSelection();
    }
    private void clearAddInboxField(){
        receiverFirstname.clear();
        receiverLastname.clear();
        receiverAddress.clear();
        senderFirstname.clear();
        senderLastname.clear();
        senderAddress.clear();
        width.clear();
        length.clear();
        height.clear();
        degree.clear();
        station.clear();
        trackingNum.clear();
        inboxImagePath = "image/emptyInbox.png";
        newInboxImageView.setImage(new Image(inboxImagePath));
    }
    private void clearRegisterPaneField(){
        firstname.clear();
        lastname.clear();
        building.getSelectionModel().clearSelection();
        floor.getSelectionModel().clearSelection();
        room.getSelectionModel().clearSelection();
        roomType.getSelectionModel().clearSelection();
        personImagePath = "image/profileDefault.jpg";
        registerImageView.setImage(new Image(personImagePath));
    }

    private void saveUpdateInboxList(){
        InboxFileDataSource inboxFileDataSource = null;
        try {
            inboxFileDataSource = new InboxFileDataSource("data", "inboxList.csv");
            inboxFileDataSource.setInboxList(inboxList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveUpdateAccountList(){
        AccountFileDataSource accountFileDataSource = null;
        try {
            accountFileDataSource = new AccountFileDataSource("data","accountList.csv");
            accountFileDataSource.setAccountList(accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveUpdateAddressList(){
        AddressListFileDataSource addressListFileDataSource = null;
        try {
            addressListFileDataSource = new AddressListFileDataSource("data","addressList.csv");
            addressListFileDataSource.setAddressList(addressList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /** PassValueToThisStage **/
    public void setWorker(Account worker) {
        this.worker = worker;
        name.setText(worker.getPersonData().getFirstName());
        username.setText(worker.getUsername());
        profileImageView.setImage(new Image(worker.getImagePath()));
    }
    public void setInboxList(InboxList inboxList) {
        this.inboxList = inboxList;
        showInboxData();
    }
    public void setAddressList(AddressList addressList) {
        this.addressList = addressList;
        showAddressData();
    }
    public void setAccountList(AccountList accountList) {
        this.accountList = accountList;
    }
}
