package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.sql.SQLOutput;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class ButtonController {

    @FXML
    private TextField building_find_ID;
    @FXML
    private TextField building_delete_ID;

    public void building_id_ButtonClicked() {
        int id = Integer.parseInt(building_find_ID.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Building Finder");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.findBuilding(id));
        alert.showAndWait();
    }
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
        ApplicationDisplay.changeScene("/DatabaseMenu.fxml");
    }

    //    public void registerScene() throws IOException {
    //        ApplicationDisplay.changeScene();
    //    }

    /**
     * Handles clicking the remove button.
     */
    public void deleteBuildingButtonClicked() {
        int id = Integer.parseInt(building_delete_ID.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Building remover");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.deleteBuilding(id));
        alert.showAndWait();
    }
}
