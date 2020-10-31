package project.neverLand.helper;

import javafx.scene.control.Alert;

public class AlertDefined {

    public static void alertWarning(String header) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("WARNING");
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    public static void alertNormal(String header) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Completed");
        alert.setHeaderText(header);
        alert.showAndWait();
    }
}
