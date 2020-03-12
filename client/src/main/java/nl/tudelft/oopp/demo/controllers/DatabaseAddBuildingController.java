package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Takes care of the functionality of the DataBaseAddBuilding.fxml file
 */
public class DatabaseAddBuildingController {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField streetTextField;
    @FXML
    private TextField houseNumberTextField;

    /**
     * Switches scene to DatabaseAddBuildings.fxml
     * @throws IOException Input will be valid
     */
    public void databaseAddBuildings() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddBuildings.fxml");
    }

    /**
     * Switches scene to DatabaseAddRooms.fxml
     * @throws IOException Input will be valid
     */
    public void databaseAddRooms() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddRooms.fxml");
    }

    /**
     * Changes to DatabaseMenu.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void databaseBuildingMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMenu.fxml");
    }

    /**
     * Changes to DatabaseRoomMenu.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void databaseRoomMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseRoomMenu.fxml");
    }

    /**
     * Adds building to the database
     * @param actionEvent actionEvent parameter.
     */
    public void databaseAddBuilding(ActionEvent actionEvent) throws UnsupportedEncodingException {
        String name = nameTextField.getText();
        String street = streetTextField.getText();
        int houseNumber = Integer.parseInt(houseNumberTextField.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Building adder");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.addBuilding(name, street, houseNumber));
        alert.showAndWait();
    }
    /**
     * returns to the main menu
     * @param actionEvent the event is clicking the menu item
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */

    public void mainMenu(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }
}
