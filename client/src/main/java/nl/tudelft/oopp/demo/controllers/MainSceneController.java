package nl.tudelft.oopp.demo.controllers;

import javafx.scene.control.Alert;
import nl.tudelft.oopp.demo.communication.ServerCommunication;

public class MainSceneController {

    /**
     * Handles clicking the button.
     */
    public void quoteButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quote for you");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.getQuote());
        alert.showAndWait();
    }
    public void userButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User for you");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.getUser());
        alert.showAndWait();
    }
}
