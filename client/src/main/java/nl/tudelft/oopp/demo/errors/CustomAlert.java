package nl.tudelft.oopp.demo.errors;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

public class CustomAlert {

    /**
     * Shows an information alert
     * @param alertMessage the context of the alert.
     */
    public static void informationAlert(String alertMessage) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(alertMessage);
        alert.initStyle(StageStyle.UNDECORATED);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(CustomAlert.class.getResource("/AlertInformation.css").toExternalForm());
        alert.showAndWait();
    }

    /**
     * Shows a warning alert
     * @param alertMessage the context of the alert.
     */
    public static void warningAlert(String alertMessage) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.WARNING);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(alertMessage);
        alert.initStyle(StageStyle.UNDECORATED);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(CustomAlert.class.getResource("/AlertWarning.css").toExternalForm());
        alert.showAndWait();
    }

    /**
     * Shows an error alert
     * @param alertMessage the context of the alert.
     */
    public static void errorAlert(String alertMessage) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.ERROR);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(alertMessage);
        alert.initStyle(StageStyle.UNDECORATED);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(CustomAlert.class.getResource("/AlertError.css").toExternalForm());
        alert.showAndWait();
    }
}
