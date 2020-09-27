package project.neverland.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.neverland.models.*;
import project.neverland.models.Package;

import java.io.IOException;

public class AddInboxController {
    private InboxList inboxList;

    @FXML
    private TextField firstNameSender, lastNameSender, addressSender;
    @FXML
    private TextField firstNameReceiver, lastNameReceiver, addressReceiver;
    @FXML
    private TextField size, degree;
    @FXML
    private TextField stationName;
    @FXML
    private TextField trackingNum;
    @FXML
    private Button add, cancel;

    @FXML
    public void initialize() {
    }

    public void addInboxBtn(ActionEvent event) throws IOException{

        if(!checkAllBoxNull()) {
            Mail mail;
            if (isPackage()) {
                mail = new Package(new Person(firstNameSender.getText(), lastNameSender.getText()),
                        addressSender.getText(),
                        new Person(firstNameReceiver.getText(), lastNameReceiver.getText()),
                        addressReceiver.getText(),
                        size.getText(), stationName.getText(),
                        trackingNum.getText());
            } else if (isDocument()) {
                mail = new Document(new Person(firstNameSender.getText(), lastNameSender.getText()),
                        addressSender.getText(),
                        new Person(firstNameReceiver.getText(), lastNameReceiver.getText()),
                        addressReceiver.getText(),
                        size.getText(), degree.getText());
            } else {
                mail = new Mail(new Person(firstNameSender.getText(), lastNameSender.getText()),
                        addressSender.getText(),
                        new Person(firstNameReceiver.getText(), lastNameReceiver.getText()),
                        addressReceiver.getText(),
                        size.getText());
            }
            inboxList.addInbox(mail);
            System.out.println(inboxList.toNotReceivedList().toString());
            Button b = (Button) event.getSource();
            Stage stage = (Stage) b.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/workerStage.fxml"));
            stage.setScene(new Scene(loader.load(), 960, 600));
            WorkerStageController workerStageController = loader.getController();
            workerStageController.setInboxList(inboxList);

        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("failed to addInbox");
            alert.setHeaderText("please input all box");
            alert.showAndWait();
        }
    }

    public void cancelBtn(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/workerStage.fxml"));
        stage.setScene(new Scene(loader.load(), 960, 600));
    }
    private boolean checkAllBoxNull(){
        return  firstNameSender.getText().equals("") ||
                lastNameSender.getText().equals("") ||
                addressSender.getText().equals("") ||
                firstNameReceiver.getText().equals("") ||
                lastNameReceiver.getText().equals("") ||
                addressReceiver.getText().equals("") ||
                size.getText().equals("");
    }
    private boolean isPackage(){
        return !stationName.getText().equals("") && !trackingNum.getText().equals("");
    }
    private boolean isDocument(){
        return !degree.getText().equals("");
    }

    public void setInboxList(InboxList inboxList) {
        this.inboxList = inboxList;
    }
}
