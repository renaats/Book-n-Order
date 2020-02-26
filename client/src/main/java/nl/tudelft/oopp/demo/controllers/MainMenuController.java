package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.MainMenuDisplay;
import nl.tudelft.oopp.demo.views.QuoteDisplay;

import java.io.IOException;
import java.net.URL;

public class MainMenuController {

    /**
     * Handles clicking the food button.
     */
    public void foodsSelsected() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("not implememnted");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    /**
     * Handles clicking the bikes button.
     */
    public void bikesSelected() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("not implememnted");
        alert.setHeaderText(null);
        alert.showAndWait();
    }
    /**
     * Handles clicking the rooms button. Should send you to the mainScene.
     */
    public void roomsSelected() throws IOException {
        QuoteDisplay.main(new String[0]);
    }
}
