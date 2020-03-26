package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "DatabaseAddBuildings.fxml" file
 */
public class DatabaseAddBuildingController {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField streetTextField;
    @FXML
    private TextField houseNumberTextField;

    /**
     * return to the database main menu when the home icon is clicked
     * @throws IOException this should not throw an exception, since the input is always the same
     */

    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * sends the user to the add building view
     * @throws IOException this should not throw an exception, since the input is always the same
     */

    public void goToAddBuildings() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddBuildings.fxml");
    }

    /**
     * sends the user to the edit building view
     * @throws IOException this should not throw an exception, since the input is always the same
     */
    public void goToEditBuildings() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseEditBuildings.fxml");
    }

    /**
     * Adds building to the database
     */
    public void databaseAddBuilding() {
        String name = null;
        String street = null;
        // -1 is a placeholder since you cannot initialize an empty integer.
        int houseNumber = -1;
        try {
            name = nameTextField.getText();
            street = streetTextField.getText();
            if (name.equals("") || street.equals("")) {
                CustomAlert.warningAlert("Missing attributes.");
                return;
            }
            try {
                houseNumber = Integer.parseInt(houseNumberTextField.getText());
            } catch (Exception e) {
                CustomAlert.errorAlert("Not an integer.");
                return;
            }
        } catch (Exception e) {
            CustomAlert.errorAlert("Could not parse attributes.");
        }
        String response = ServerCommunication.addBuilding(name, street, houseNumber);
        if (response.equals("Successfully added!")) {
            CustomAlert.informationAlert(response);
        } else {
            CustomAlert.errorAlert(response);
        }
    }

    /**
     * Goes back to the database building menu when the icon is clicked
     * @throws IOException this should not throw an exception, since the input is always the same
     */
    public void goToBuildingMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseBuildingMenu.fxml");
    }
}
