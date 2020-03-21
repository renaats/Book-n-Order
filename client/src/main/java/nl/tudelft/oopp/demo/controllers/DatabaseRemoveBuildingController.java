package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;


public class DatabaseRemoveBuildingController {

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
     * sends the user to the remove building view
     * @throws IOException this should not throw an exception, since the input is always the same
     */
    public void goToRemoveBuildings() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseRemoveBuildings.fxml");
    }

    /**
     * sends the user to the edit building view
     * @throws IOException this should not throw an exception, since the input is always the same
     */
    public void goToEditBuildings() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseEditBuildings.fxml");
    }

    /**
     * return to the database building menu when the building icon on the menu bar is clicked
     * @throws IOException this should not throw an exception, since the input is always the same
     */
    public void goToBuildingMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseBuildingMenu.fxml");
    }
}
