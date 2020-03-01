package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.ServerCommunication;

import java.io.IOException;

public class MainSceneController {

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
     * Handles clicking the remove button.
     */
    public void deleteBuildingButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Removed a building ");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.deleteBuilding());
        alert.showAndWait();
    }

    public void goBackToMenu (ActionEvent actionEvent) throws IOException {
        Parent roomSelectParent = FXMLLoader.load(getClass().getResource("/mainMenu.fxml"));
        Scene roomSelectScene = new Scene(roomSelectParent);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(roomSelectScene);
        window.show();
    }
}
