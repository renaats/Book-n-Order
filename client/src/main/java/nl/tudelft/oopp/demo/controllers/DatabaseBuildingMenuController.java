package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;


public class DatabaseBuildingMenuController {
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
}
