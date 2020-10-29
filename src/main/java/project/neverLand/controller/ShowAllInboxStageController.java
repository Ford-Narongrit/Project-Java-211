package project.neverLand.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import project.neverLand.models.InboxList;
import project.neverLand.models.Mail;
import project.neverLand.services.StringConfiguration;

import java.util.ArrayList;

public class ShowAllInboxStageController {
    private ObservableList inboxObservableList;
    private InboxList inboxList;

    @FXML
    private AnchorPane showAllInboxAnchorPane;
    @FXML
    private TableView<Mail> inboxTable;

    public void setShowAllInboxAnchorPane(String path) {
        showAllInboxAnchorPane.getStylesheets().add(getClass().getResource(path).toExternalForm());
    }

    public void initialize() {
    }

    private void showData() {
        inboxObservableList = FXCollections.observableArrayList(inboxList.toList());
        inboxTable.setItems(inboxObservableList);

        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:Date", "field:date", "width:0.2"));
        configs.add(new StringConfiguration("title:Room Number", "field:receiverLocation", "width:0.15"));
        configs.add(new StringConfiguration("title:Receiver Time", "field:receivedTime", "width:0.15"));
        configs.add(new StringConfiguration("title:Receiver", "field:receiver", "width:0.175"));
        configs.add(new StringConfiguration("title:Sender", "field:sender", "width:0.175"));
        configs.add(new StringConfiguration("title:Size", "field:size", "width:0.05"));
        configs.add(new StringConfiguration("title:Worker", "field:workerName", "width:0.15"));
        configs.add(new StringConfiguration("title:Received", "field:received", "width:0.1"));

        for (StringConfiguration conf : configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.prefWidthProperty().bind(inboxTable.widthProperty().multiply(Double.parseDouble(conf.get("width"))));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            col.setResizable(false);
            inboxTable.getColumns().add(col);
        }
    }

    public void setInboxList(InboxList inboxList) {
        this.inboxList = inboxList;
        showData();
    }
}
