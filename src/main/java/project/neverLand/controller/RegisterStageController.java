package project.neverLand.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.neverLand.models.*;
import project.neverLand.helper.AlertDefined;
import project.neverLand.services.fileDataSource.AccountFileDataSource;
import project.neverLand.services.fileDataSource.ImageDataSource;


import java.io.IOException;

public class RegisterStageController {
    private AccountList accountList;
    private AddressList addressList;
    private String imagePath;

    @FXML private AnchorPane registerAnchorPane;

    @FXML private TextField username, firstname, lastname;
    @FXML private PasswordField password, confirmPassword;
    @FXML private Button create, cancel, chooseImage;
    @FXML private ImageView registerImageView;

    public void setRegisterAnchorPane(String path) {
        registerAnchorPane.getStylesheets().add(getClass().getResource(path).toExternalForm());
    }

    public void initialize() {
        imagePath = "image/profileDefault.jpg";
    }

    public void createBtnAction(ActionEvent event){
        try {
            accountList.register("resident", firstname.getText(),lastname.getText(),username.getText(),password.getText(),confirmPassword.getText(), imagePath, addressList);
            AccountFileDataSource accountFileDataSource = new AccountFileDataSource("data","accountList.csv");
            accountFileDataSource.setAccountList(accountList);
            AlertDefined.alertWarning("complete");
            returnHomeAction(event);

        } catch (IllegalAccessException e){
            AlertDefined.alertWarning(e.getMessage());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void chooseImage(ActionEvent event){
        ImageDataSource imageDateSource = new ImageDataSource();
        imagePath = imageDateSource.getPathForFileChooser(event);
        registerImageView.setImage(new Image(imagePath,150.00,150.00,false,false));
    }

    public void returnHomeAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginStage.fxml"));
        stage.setScene(new Scene(loader.load(), 960, 600));
    }

    public void setAccountList(AccountList accountList) {
        this.accountList = accountList;
    }
    public void setAddressList(AddressList addressList) {
        this.addressList = addressList;
    }
}
