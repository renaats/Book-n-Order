package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class LeaveBikeFeedbackController {

    @FXML
    public TextField bikeFeedbackText;

    public final int maxFieldLength = 150;

    public void submitBikeFeedback() {
        if (bikeFeedbackText.getText() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Authenticator");
            alert.setHeaderText(null);
            alert.setContentText("Please type something.");
            alert.showAndWait();
        } else {
            //ServerCommunication.addBikeFeedback(client, recipient, reservationId, current time, bikeFeedbackText.text());
        }
    }
}
