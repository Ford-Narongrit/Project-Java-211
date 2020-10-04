package project.neverland.services;

import javafx.scene.control.Alert;

public class AlertDefined {
    public static void alertWarning(String title, String header){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
}
