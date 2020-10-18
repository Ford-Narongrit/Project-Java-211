package project.neverLand.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WorkerStageController {
    private AccountList accountList;
    private InboxList inboxList;
    private AddressList addressList;
    private Account worker;

    private ImageDataSource imageDateSource;
    private String personImagePath = "image/profileDefault.jpg";
    private String inboxImagePath = "image/emptyInbox.png";

    @FXML private AnchorPane workerAnchorPane;

    /** infoPane **/
    @FXML private Pane infoPane;
    @FXML private Button infoBtn;
    @FXML private TableView<Address> addressTable;
    private ObservableList addressObservableList;
    private Address selectedAddress;
    @FXML private TextField searchInfoPane;
    @FXML private VBox personBox;

    /** managePane **/
    @FXML private Pane managePane;
    @FXML private Button manageBtn, receivedBtn, showAllInboxBtn;
    @FXML private TableView<Mail> inboxTable;
    private ObservableList inboxObservableList;
    private Mail selectedMail;
    @FXML private Label receiver, sender, size;
    @FXML private TextField searchManagePane;
    @FXML private ImageView inboxImageView;

    /** addNewInboxPane **/
    @FXML private Pane addNewInboxPane;
    @FXML private TextField receiverFirstname, receiverLastname, receiverAddress;
    @FXML private TextField senderFirstname, senderLastname, senderAddress;
    @FXML private TextField width, length, height, degree, station, trackingNum;
    @FXML private ImageView newInboxImageView;
    @FXML private Button addNewInboxBtn, addNewInboxCancelBtn, chooseImageBtn;
    @FXML private ComboBox sizeComboBox, typeComboBox;

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

    public void setWorkerAnchorPane(String path) {
        workerAnchorPane.getStylesheets().add(getClass().getResource(path).toExternalForm());
    }

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
        searchManagePane.textProperty().addListener((observable, oldValue, newValue) -> {
            inboxTable.getColumns().clear();
            showInboxData(inboxList.toRoomNumber(newValue));
        });
        searchInfoPane.textProperty().addListener((observable, oldValue, newValue) -> {
            addressTable.getColumns().clear();
            showAddressData(addressList.toPersonList(newValue));
        });

        receiverFirstname.textProperty().addListener(((observable, oldValue, newValue) -> {
            String location = addressList.findRoomNumber(newValue + receiverLastname.getText());
            if(location != "")
                receiverAddress.setText(location);
            else
                receiverAddress.setText("");
        }));
        receiverLastname.textProperty().addListener((observable, oldValue, newValue) -> {
            String location = addressList.findRoomNumber(receiverFirstname.getText() + newValue);
            if(location != "")
                receiverAddress.setText(location);
            else
                receiverAddress.setText("");
        });

        typeComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                sizeComboBox.getSelectionModel().clearSelection();
                sizeComboBox.getItems().clear();
                resetDisable();
                clearAddInboxField();
                if(newValue == "Mail"){
                    sizeComboBox.getItems().addAll("C6","C5","C4","DL","Other");
                    disableToMail();
                }
                else if(newValue == "Document"){
                    sizeComboBox.getItems().addAll("A4","A5","A8","Other");
                    disableToDocument();
                }
                else if(newValue == "Package"){
                    sizeComboBox.getItems().addAll("A","2A","B","2B","C","2C","Other");
                    disableToPackage();
                }
            }
        });
        sizeComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                disableSize();
                if(newValue != null) {
                    switch (newValue) {
                        /** Mail **/
                        case "C6":
                            setWidthLength("11.4", "16.2");
                            break;
                        case "C5":
                            setWidthLength("16.2", "22.9");
                            break;
                        case "C4":
                            setWidthLength("22.9", "32.4");
                            break;
                        case "DL":
                            setWidthLength("11", "22");
                            break;
                        /** Document **/
                        case "A4":
                            setWidthLength("21", "29.7");
                            break;
                        case "A5":
                            setWidthLength("14.8", "21.0");
                            break;
                        case "A8":
                            setWidthLength("5.2", "7.4");
                            break;
                        /** package **/
                        case "A":
                            setWidthLengthHeight("14", "20", "7");
                            break;
                        case "2A":
                            setWidthLengthHeight("14", "20", "12");
                            break;
                        case "B":
                            setWidthLengthHeight("17", "25", "9");
                            break;
                        case "2B":
                            setWidthLengthHeight("16", "25", "18");
                            break;
                        case "C":
                            setWidthLengthHeight("20", "30", "11");
                            break;
                        case "2C":
                            setWidthLengthHeight("20", "30", "22");
                            break;
                        case "Other":
                            unDisableSize(typeComboBox.getSelectionModel().getSelectedItem().toString());
                            System.out.println(typeComboBox.getSelectionModel().getSelectedItem());
                            break;
                    }
                }
            }
        });
    }
    private void setTextComboBox(){
        building.getItems().addAll("1","2");
        floor.getItems().addAll("1","2","3","4","5","6","7","8");
        roomType.getItems().addAll("one bedroom","two bedrooms");
        room.getItems().addAll("1","2","3","4","5","6","7","8","9","10");

        typeComboBox.getItems().addAll("Mail", "Document", "Package");
        typeComboBox.getSelectionModel().select("Mail");
        sizeComboBox.getItems().addAll("C6","C5","C4","DL","Other");
        disableToMail();
    }
    private void disableToMail(){
        width.setDisable(false);
        length.setDisable(false);
        height.setDisable(true);
        degree.setDisable(true);
        station.setDisable(true);
        trackingNum.setDisable(true);
    }
    private void disableToPackage(){
        width.setDisable(false);
        length.setDisable(false);
        degree.setDisable(true);
    }
    private void disableToDocument(){
        width.setDisable(false);
        length.setDisable(false);
        height.setDisable(true);
        station.setDisable(true);
        trackingNum.setDisable(true);
    }
    private void resetDisable(){
        height.setDisable(false);
        station.setDisable(false);
        trackingNum.setDisable(false);
        degree.setDisable(false);
    }
    private void disableSize(){
        height.setDisable(true);
        width.setDisable(true);
        length.setDisable(true);
    }
    private void unDisableSize(String type){
        if(type == "Package"){
            height.setDisable(false);
        }
        width.setDisable(false);
        length.setDisable(false);
    }
    private void setWidthLength(String width, String length){
        this.width.setText(width);
        this.length.setText(length);
    }
    private void setWidthLengthHeight(String width, String length, String height){
        this.width.setText(width);
        this.length.setText(length);
        this.height.setText(height);
    }

    /** infoPane **/
    public void infoBtnAction(){
        infoPane.toFront();
    }
    private void showAddressData(ArrayList<Address> addressList){
        addressObservableList = FXCollections.observableArrayList(addressList);
        addressTable.setItems(addressObservableList);

        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:Room Number", "field:roomNumber", "width:0.5"));
        configs.add(new StringConfiguration("title:Room Type", "field:roomType", "width:0.5"));

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
            ImageView imageView = new ImageView(new Image(person.getImagePath(),150.00,150.00,false,false));
            personBox.getChildren().add(imageView);
            Label personName = new Label(person.toString());
            personName.setPadding(new Insets(20,0,20,0));
            personName.getStyleClass().add("bigLabel");
            personBox.getChildren().add(personName);
        }
    }
    //todo create method can edit personData

    /** managePane **/
    public void manageBtnAction(){
        managePane.toFront();
    }
    private void showInboxData(ArrayList<Mail> inboxList) {
        inboxObservableList = FXCollections.observableArrayList(inboxList);
        inboxTable.setItems(inboxObservableList);

        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:Date", "field:date", "width:0.3"));
        configs.add(new StringConfiguration("title:Room Number", "field:senderLocation", "width:0.3"));
        configs.add(new StringConfiguration("title:Worker", "field:workerName", "width:0.4"));

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
        inboxImageView.setImage(new Image(selectedMail.getImagePath(),150.00,150.00,false,false));
        receiver.setText(selectedMail.getReceiver().getFirstName());
        sender.setText(selectedMail.getSender().getFirstName());
        size.setText(String.valueOf(selectedMail.getSize()));
    }
    public void showAllInboxBtnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/showAllInboxStage.fxml"));
        stage.setScene(new Scene(loader.load(),800,600));
        stage.initModality(Modality.APPLICATION_MODAL);
        ShowAllInboxStageController showAllInboxStageController = loader.getController();
        showAllInboxStageController.setInboxList(inboxList);

        stage.show();
    }
    public void receivedInboxBtnAction(){
        selectedMail.setReceived(true);
        saveInboxList();
        clearSelectMail();
        updateInboxTable();
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd--HH:mm:ss");
        Date date = new Date();
        if(!checkAllBoxNull()) {
            Mail mail;
            if (isPackage()) {
                mail = new Package(new Person(senderFirstname.getText(), senderLastname.getText()),
                        senderAddress.getText(),
                        new Person(receiverFirstname.getText(), receiverLastname.getText()),
                        receiverAddress.getText(),
                        station.getText(), trackingNum.getText(), inboxImagePath,
                        Double.parseDouble(width.getText()), Double.parseDouble(length.getText()),
                        Double.parseDouble(height.getText()), dateFormat.format(date), worker.getUsername());
            } else if (isDocument()) {
                mail = new Document(new Person(senderFirstname.getText(), senderLastname.getText()),
                        senderAddress.getText(),
                        new Person(receiverFirstname.getText(), receiverLastname.getText()),
                        receiverAddress.getText(),
                        degree.getText(),
                        inboxImagePath, Double.parseDouble(width.getText()), Double.parseDouble(length.getText()), dateFormat.format(date), worker.getUsername());
            } else {
                mail = new Mail(new Person(senderFirstname.getText(), senderLastname.getText()),
                        senderAddress.getText(),
                        new Person(receiverFirstname.getText(), receiverLastname.getText()),
                        receiverAddress.getText(),
                        inboxImagePath, Double.parseDouble(width.getText()), Double.parseDouble(length.getText()), dateFormat.format(date), worker.getUsername());
            }
            try {
                addressList.findAddress(receiverAddress.getText());
                mail.calSize();
                inboxList.addInbox(mail);
                saveInboxList();
                clearAddInboxField();
                updateInboxTable();
            } catch (IllegalAccessException e) {
                AlertDefined.alertWarning(e.getMessage());
            }
        }
        else{
            AlertDefined.alertWarning("please input all box");
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
    public void registerChooseImage(ActionEvent event){
        personImagePath = imageDateSource.getPathForFileChooser(event);
        registerImageView.setImage(new Image(personImagePath,150.00,150.00,false,false));
    }
    public void createBtnAction(){
        try {
            addressList.findAddress((String) building.getValue() + "-" + (String) floor.getValue() + "/" + (String) room.getValue());
            addressList.getCurrentAddress().addPersonToRoom(new Person(firstname.getText(), lastname.getText(), personImagePath));
            saveAddressList();
            clearRegisterPaneField();
            updateAddressTable();
        }
        catch (IllegalAccessException e){
            AlertDefined.alertWarning(e.getMessage());
        }
    }
    public void registerCancelBtnAction(){
        clearRegisterPaneField();
    }

    /** profilePane **/
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
            worker.setPassword(customDialog.getOutput());
        }
        saveAccountList();
    }
    public void changeProfile(ActionEvent event){
        personImagePath = imageDateSource.getPathForFileChooser(event);
        profileImageView.setImage(new Image(personImagePath,150.00,150.00,false,false));
        worker.setImagePath(personImagePath);
        personImagePath = "image/profileDefault.jpg";
        saveAccountList();
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
        inboxImageView.setImage(new Image(inboxImagePath,150.00,150.00,false,false));
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
        newInboxImageView.setImage(new Image(inboxImagePath,150.00,150.00,false,false));
    }
    private void clearRegisterPaneField(){
        firstname.clear();
        lastname.clear();
        building.getSelectionModel().clearSelection();
        floor.getSelectionModel().clearSelection();
        room.getSelectionModel().clearSelection();
        roomType.getSelectionModel().clearSelection();
        personImagePath = "image/profileDefault.jpg";
        registerImageView.setImage(new Image(personImagePath,150.00,150.00,false,false));
    }

    private void saveInboxList(){
        InboxFileDataSource inboxFileDataSource = null;
        try {
            inboxFileDataSource = new InboxFileDataSource("data", "inboxList.csv");
            inboxFileDataSource.setInboxList(inboxList);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    private void saveAddressList(){
        AddressListFileDataSource addressListFileDataSource = null;
        try {
            addressListFileDataSource = new AddressListFileDataSource("data","addressList.csv");
            addressListFileDataSource.setAddressList(addressList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void updateInboxTable(){
        inboxTable.getColumns().clear();
        showInboxData(inboxList.toNotReceivedList());
    }
    private void updateAddressTable() {
        addressTable.getColumns().clear();
        showAddressData(addressList.toList());
    }

    /** PassValueToThisStage **/
    public void setWorker(Account worker) {
        this.worker = worker;
        name.setText(worker.getPersonData().getFirstName());
        username.setText(worker.getUsername());
        profileImageView.setImage(new Image(worker.getImagePath(),150.00,150.00,false,false));
    }
    public void setInboxList(InboxList inboxList) {
        this.inboxList = inboxList;
        showInboxData(inboxList.toNotReceivedList());
    }
    public void setAddressList(AddressList addressList) {
        this.addressList = addressList;
        showAddressData(addressList.toList());
    }
    public void setAccountList(AccountList accountList) {
        this.accountList = accountList;
    }
}
