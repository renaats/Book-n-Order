package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import javafx.scene.control.Alert;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class ButtonController {

    /**
     * Handles clicking the quote button.
     */
    public void quoteButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quote for you");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.getQuote());
        alert.showAndWait();
    }

    /**
     * Handles clicking the user button.
     */
    public void userButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User for you");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.getUser());
        alert.showAndWait();
    }

    /**
     * Handles clicking the add button.
     */
    public void addBuildingButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("A new building has been added!");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.postBuilding());
        alert.showAndWait();
    }

    /**
     * Handles clicking the list button.
     */
    public void listBuildingsButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("All buildings:");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.getBuilding());
        alert.showAndWait();
    }

    /**
     * Changes to mainScene.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/mainScene.fxml");
    }

    //    public void registerScene() throws IOException {
    //        ApplicationDisplay.changeScene();
    //    }

    /**
     * Handles clicking the remove button.
     */
    public void deleteBuildingButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Removed a building ");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.deleteBuilding());
        alert.showAndWait();
    }
}
