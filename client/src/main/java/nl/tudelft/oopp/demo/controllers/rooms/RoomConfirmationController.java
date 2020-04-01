package nl.tudelft.oopp.demo.controllers.rooms;

import java.io.IOException;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "roomConfirmation.fxml" file
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
