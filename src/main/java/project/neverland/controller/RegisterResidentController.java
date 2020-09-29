package project.neverland.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.neverland.models.Address;
import project.neverland.models.AddressList;
import project.neverland.models.Person;

import java.io.IOException;

public class RegisterResidentController {
    private AddressList addressList;
    @FXML
    TextField firstName, lastName, building, floor, roomNumber, roomType;
    @FXML
    Button create, cancel;
    @FXML
    public void initialize() {
        addressList = new AddressList();
    }

    public void createBtnAction(ActionEvent event) throws IOException {
        Address address = new Address(building.getText(),Integer.parseInt(floor.getText()),roomNumber.getText(),roomType.getText());
        address.addPersonInRoom(new Person(firstName.getText(),lastName.getText()));
        addressList.addAddress(address);

        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/workerStage.fxml"));
        stage.setScene(new Scene(loader.load(), 960, 600));
    }

    public void cancelBtnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/workerStage.fxml"));
        stage.setScene(new Scene(loader.load(), 960, 600));
    }



}
