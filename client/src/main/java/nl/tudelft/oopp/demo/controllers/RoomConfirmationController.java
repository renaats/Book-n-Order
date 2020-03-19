package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the content correct content into the FXML objects that need to display server information and
 * controls all the user inputs thought the GUI made by the user in the "roomConfirmation.fxml" file
 */
public class RoomConfirmationController {

    /**
     * will change to main menu when the background is clicked
     * @throws IOException should never throw an exception, since the input is always the same
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }
}
