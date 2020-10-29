package project.neverLand.helper;

import javafx.scene.control.Alert;

public class AlertDefined {
    private static Alert alert = new Alert(Alert.AlertType.WARNING);

    public static void alertWarning(String header) {
        alert.setTitle("WARNING");
        alert.setHeaderText(header);
        alert.showAndWait();
    }
}
