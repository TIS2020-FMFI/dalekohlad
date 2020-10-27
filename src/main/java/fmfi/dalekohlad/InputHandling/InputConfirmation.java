package fmfi.dalekohlad.InputHandling;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class InputConfirmation {
    static Boolean confirm(String question, String... header) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(header.length == 0?"Potvrdte neobvyklý vstup":header[0]);
        alert.setContentText(question);

        Optional<ButtonType> result = alert.showAndWait();
        return ((Optional) result).get() == ButtonType.OK;
    }
}
