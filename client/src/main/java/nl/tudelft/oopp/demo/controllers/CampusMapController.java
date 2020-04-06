package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "CampusMap.fxml" file
 */
public class CampusMapController {

    /**
     * method changes the view to that of the main menu
     * @throws IOException the method will never throw an exception
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenu.fxml");
    }

    /**
     * method changes the view to that of the contact information
     * @throws IOException the method will never throw an exception
     */
    public void goToContactInformation() throws IOException {
        ApplicationDisplay.changeScene("/ContactInformation.fxml");
    }
}
