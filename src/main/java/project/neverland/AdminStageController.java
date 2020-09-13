package project.neverland;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class AdminStageController {
    private Person admin;

    private PersonList people;
    private DataBase dataBase;
    private Person selectedPerson;
    private ObservableList peopleObservableList;

    @FXML private TableView<Person> peopleTable;
    @FXML
    public void initialize(){
        peopleTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                selectedPerson = newValue;
                System.out.println(selectedPerson.toString());
            }
        });
    }
    private void showData() {
        peopleObservableList = FXCollections.observableArrayList(people.toList());
        peopleTable.setItems(peopleObservableList);

        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:Username", "field:username", "width:0.2"));
        configs.add(new StringConfiguration("title:Firstname", "field:firstName", "width:0.4"));
        configs.add(new StringConfiguration("title:Lastname", "field:lastName", "width:0.4"));

        for (StringConfiguration conf: configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.prefWidthProperty().bind(peopleTable.widthProperty().multiply(Double.parseDouble(conf.get("width"))));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            col.setResizable(false);
            peopleTable.getColumns().add(col);
        }
    }
    public void setAdmin(Person admin) {
        this.admin = admin;
    }

    public void setPeople(PersonList people) {
        this.people = people;
        showData();
    }
}
