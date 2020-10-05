package project.neverLand.services;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Optional;

public class CustomDialog {
    private Dialog<Pair<String, String>> dialog;
    private ArrayList<PasswordField> passwordFieldsList;
    private String output;
    private boolean checkNotnull;

    public CustomDialog() {
        this.dialog = new Dialog<>();
        passwordFieldsList = new ArrayList<>();
        checkNotnull = true;
    }

    public String getOutput() {
        return output;
    }

    public void setTitleAndHeaderDialog(String titleName, String headerText) {
        dialog.setTitle(titleName);
        dialog.setHeaderText(headerText);
    }

    public void addButton(String nameButton) {
        ButtonType confirmButtonType = new ButtonType(nameButton, ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);
        Node confirmButton = dialog.getDialogPane().lookupButton(confirmButtonType);
        confirmButton.setDisable(true);
    }

    public void createFields() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        PasswordField confirmPassword = new PasswordField();
        confirmPassword.setPromptText("Confirm Password");

        grid.add(new Label("Password:"), 0, 0);
        grid.add(password, 1, 0);
        grid.add(new Label("Confirm Password:"), 0, 1);
        grid.add(confirmPassword, 1, 1);

        passwordFieldsList.add(password);
        passwordFieldsList.add(confirmPassword);

        password.textProperty().addListener((observable, oldValue, newValue) -> {
            dialog.getDialogPane().lookupButton(dialog.getDialogPane().getButtonTypes().get(0)).setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void setCheckNotnull(boolean checkNotnull) {
        this.checkNotnull = checkNotnull;
    }

    public boolean isCheckNotnull() {
        return checkNotnull && (getOutput() != null);
    }

    public void getResult() {
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == dialog.getDialogPane().getButtonTypes().get(0)) {
                return new Pair<>(passwordFieldsList.get(0).getText(), passwordFieldsList.get(1).getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            if (usernamePassword.getKey().equals(usernamePassword.getValue())) {
                setOutput(usernamePassword.getKey());
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Can not found account");
                alert.setHeaderText("Incorrect Username or Password.");
                alert.showAndWait();
                checkNotnull = false;
            }
        });
    }
}

