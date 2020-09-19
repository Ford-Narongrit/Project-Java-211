package project.neverland.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import project.neverland.models.*;
import project.neverland.services.CustomDialog;
import project.neverland.services.StringConfiguration;

import java.io.IOException;
import java.util.ArrayList;

public class WorkerStageController {
    private InboxList inboxList;
    private Mail selectedMail;
    private ObservableList mailObservableList;
    private Account worker;


    @FXML private Label header;
    @FXML private TableView<Mail> inboxTable;
    @FXML private Button rePassword,addInbox,register,home;
    @FXML private Label to, from, size;


    @FXML public void initialize(){
        inboxList = new InboxList();
        Mail a = new Mail(new Person("ford","za"),new Person("a","b"),10);
        inboxList.addInbox(a);
        a = new Mail(new Person("jui","oooo"),new Person("a","b"),10);
        inboxList.addInbox(a);
        a = new Mail(new Person("Dommm","za"),new Person("a","b"),10);
        inboxList.addInbox(a);

        showData();
        inboxTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedMail(newValue);
            }
        });
    }
    public void showSelectedMail(Mail mail) {
        selectedMail = mail;
        to.setText(selectedMail.getPersonTo().getFirstName());
        from.setText(selectedMail.getPersonFrom().getFirstName());
        size.setText(String.valueOf(selectedMail.getSize()));
    }

    private void showData() {
        mailObservableList = FXCollections.observableArrayList(inboxList.toList());
        inboxTable.setItems(mailObservableList);

        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:personTo", "field:personTo", "width:0.2"));
        configs.add(new StringConfiguration("title:personFrom", "field:personFrom", "width:0.3"));
        configs.add(new StringConfiguration("title:size", "field:size", "width:0.3"));

        for (StringConfiguration conf : configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.prefWidthProperty().bind(inboxTable.widthProperty().multiply(Double.parseDouble(conf.get("width"))));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            col.setResizable(false);
            inboxTable.getColumns().add(col);
        }
    }

    @FXML
    private void reSetPassword() throws IOException {
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

    @FXML
    private void homeBtnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginStage.fxml"));
        stage.setScene(new Scene(loader.load(), 960, 600));

    }

    public void addInboxAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/addInboxStage.fxml"));
        stage.setScene(new Scene(loader.load(), 960, 600));


    }

    public void registerResidentAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/registerResidentStage.fxml"));
        stage.setScene(new Scene(loader.load(), 960, 600));


    }

    public void setWorker(Account worker) {
        this.worker = worker;
    }

}
