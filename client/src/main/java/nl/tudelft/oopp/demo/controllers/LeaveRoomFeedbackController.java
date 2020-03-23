package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


public class LeaveRoomFeedbackController {

    @FXML
    public TextField roomFeedbackText;

    public final int maxFieldLength = 150;

    public void submitRoomFeedback() {
        if (roomFeedbackText.getText() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Authenticator");
            alert.setHeaderText(null);
            alert.setContentText("Please type something.");
            alert.showAndWait();
        } else {
            //ServerCommunication.addRoomFeedback(client, recipient, reservationId, current time, roomFeedbackText.text());
        }
    }
}
