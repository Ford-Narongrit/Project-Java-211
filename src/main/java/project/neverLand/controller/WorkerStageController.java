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
import project.neverLand.models.sortBy.DateSortStart;
import project.neverLand.models.sortBy.DateSortLast;
import project.neverLand.models.sortBy.RoomNumSortHigh;
import project.neverLand.models.sortBy.RoomNumSortLower;
import project.neverLand.services.CustomDialog;
import project.neverLand.services.ImageSetter;
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

    private ImageSetter imageSetter;
    private ImageDataSource imageDateSource;
    private String personImagePath;
    private String inboxImagePath;

    @FXML private AnchorPane workerAnchorPane;

    /** infoPane **/
    @FXML private Pane infoPane;
    @FXML private Button infoBtn, addNewFloor;
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
    @FXML private ComboBox sortBy;

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
    @FXML private TextField firstname, lastname, building, floor, room;
    @FXML private ComboBox roomType;
    @FXML private Button registerBtn, createBtn, registerCancelBtn, registerChooseImageBtn, createRoom;
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
        imageSetter = new ImageSetter();
        personImagePath = "image/profileDefault.jpg";
        inboxImagePath = "image/emptyInbox.png";
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
            if(!newValue.equals("")){
                showInboxData(inboxList.toRoomNumber(newValue));
            }else{
                showInboxData(inboxList.toNotReceivedList());
            }
        });
        searchInfoPane.textProperty().addListener((observable, oldValue, newValue) -> {
            addressTable.getColumns().clear();
            if(!newValue.equals("")){
                showAddressData(addressList.toPersonList(newValue));
            }else{
                showAddressData(addressList.toList());
            }

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

        setComboBoxSortBy();
        sortBy.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                inboxTable.getColumns().clear();
                if(newValue != null){
                    switch (newValue){
                        case "Recent item":
                            inboxList.sortBy(new DateSortLast());
                            break;
                        case "Old item":
                            inboxList.sortBy(new DateSortStart());
                            break;
                        case "Room number(0-9)":
                            inboxList.sortBy(new RoomNumSortHigh());
                            break;
                        case "Room number(9-0)":
                            inboxList.sortBy(new RoomNumSortLower());
                            break;
                    }
                }
                showInboxData(inboxList.toNotReceivedList());
            }
        });

        building.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,2}?")) {
                    building.setText(oldValue);
                }
            }
        });
        floor.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,2}?")) {
                    floor.setText(oldValue);
                }
            }
        });
        room.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,2}?")) {
                    room.setText(oldValue);
                }
            }
        });
    }
    private void setTextComboBox(){
        roomType.getItems().addAll("one bedroom","two bedrooms");

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
            //todo remove roomer by selectAddress;
            ImageView imageView = new ImageView();
            imageSetter.setImage(imageView, person.getImagePath());
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
    private void setComboBoxSortBy(){
        sortBy.getItems().addAll("Recent item", "Old item", "Room number(0-9)", "Room number(9-0)");
    }
    private void showInboxData(ArrayList<Mail> inboxList) {
        inboxObservableList = FXCollections.observableArrayList(inboxList);
        inboxTable.setItems(inboxObservableList);

        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:Date", "field:date", "width:0.3"));
        configs.add(new StringConfiguration("title:Room Number", "field:receiverLocation", "width:0.3"));
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
        imageSetter.setImage(inboxImageView, selectedMail.getImagePath());
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd--HH:mm:ss");
        Date date = new Date();
        selectedMail.setReceived(true);
        selectedMail.setReceivedTime(dateFormat.format(date));
        saveInboxList();
        clearSelectMail();
        updateInboxTable();
    }

    /** addNewInboxPane **/
    public void addInboxBtnAction(){
        addNewInboxPane.toFront();
    }
    public void addNewInboxChooseImage(ActionEvent event){
        inboxImagePath = imageDateSource.getPathForFileChooser(event, "inbox");
        imageSetter.setImage(newInboxImageView, inboxImagePath);
    }
    public void addNewInboxBtnAction(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd--HH:mm:ss");
        Date date = new Date();
        if(!checkAllBoxNull()) {
            try {
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
                addressList.findAddress(receiverAddress.getText());
                mail.calSize();
                inboxList.addInbox(mail);
                saveInboxList();
                clearAddInboxField();
                updateInboxTable();
            } catch (IllegalAccessException e) {
                AlertDefined.alertWarning(e.getMessage());
            } catch (NumberFormatException e){
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
        personImagePath = imageDateSource.getPathForFileChooser(event, "person");
        imageSetter.setImage(registerImageView, personImagePath);
    }
    public void createBtnAction(){
        try {
            addressList.findAddress( building.getText() + "-" + floor.getText() + "/" + room.getText());
            addressList.getCurrentAddress().addPersonToRoom(new Person(firstname.getText(), lastname.getText(), personImagePath));
            saveAddressList();
            clearRegisterPaneField();
            updateAddressTable();
        }
        catch (IllegalAccessException e){
            AlertDefined.alertWarning(e.getMessage());
        }
    }

    public void createRoomBtnAction() {
        if(building.getText().equals("") || floor.getText().equals("") || room.getText().equals("")){
            AlertDefined.alertWarning("Please enter building, floor and room");
            return;
        }
        String roomNumber = building.getText() + "-" + floor.getText() + "/" + room.getText();
        try {
            addressList.findAddress(roomNumber);
            AlertDefined.alertWarning("This room has already been added.");
        } catch (IllegalAccessException e) {
            addressList.addAddress(new Address(roomNumber, (String)roomType.getSelectionModel().getSelectedItem()));
            AlertDefined.alertWarning("complete");
        }
        saveAddressList();
        updateAddressTable();
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
        personImagePath = imageDateSource.getPathForFileChooser(event, "person");
        imageSetter.setImage(profileImageView, personImagePath);
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
        building.clear();
        floor.clear();
        room.clear();
        roomType.getSelectionModel().clearSelection();
        personImagePath = "image/profileDefault.jpg";
        imageSetter.setImage(registerImageView, personImagePath);
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
        name.setText(worker.getPersonData().toString());
        username.setText(worker.getUsername());
        imageSetter.setImage(profileImageView, worker.getImagePath());
    }
    public void setInboxList(InboxList inboxList) {
        this.inboxList = inboxList;
        inboxList.sortBy(new DateSortLast());
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
