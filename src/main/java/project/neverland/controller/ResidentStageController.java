package project.neverland.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import project.neverland.models.Account;
import project.neverland.models.Address;
import project.neverland.models.InboxList;
import project.neverland.models.Mail;
import project.neverland.services.StringConfiguration;

import java.io.IOException;
import java.util.ArrayList;

public class ResidentStageController {
    private Address address;
    private Account account;
    private InboxList inboxList;

    private Mail selectedMail;
    private ObservableList mailObservableList;

    @FXML Button inbox, profile, home;
    @FXML TableView inboxTable;
    @FXML Button changePassword;
    @FXML Pane inboxPane, profilePane;
    @FXML
    public void initialize() {
        inboxTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
//                showSelectedMail(newValue);
            }
        });
    }

    public void inboxBtnAction(){
        inboxPane.toFront();
    }
    public void profileBtnAction(){
        profilePane.toFront();
    }
    public void homeBtnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginStage.fxml"));
        stage.setScene(new Scene(loader.load(),960, 600));
    }

    private void showData() {
        mailObservableList = FXCollections.observableArrayList(inboxList.toPersonList(account.getPersonData()));
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

    public void setAddress(Address address) {
        this.address = address;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
    public void setInboxList(InboxList inboxList) {
        this.inboxList = inboxList;
        showData();
    }
}
