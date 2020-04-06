package nl.tudelft.oopp.demo.controllers.generic;

import java.io.IOException;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "ContactInformation.fxml" file
 */
public class ContactInformationController {

    /**
     * Changes current scene to MyCurrentBookings.fxml.
     * @throws IOException input will be valid.
     */
    public void myCurrentBookings() throws IOException {
        ApplicationDisplay.changeScene("/MyCurrentBookings.fxml");
    }

    /**
     * Changes current scene to MyPreviousBookings.fxml.
     * @throws IOException input will be valid.
     */
    public void myPreviousBookings() throws IOException {
        ApplicationDisplay.changeScene("/MyPreviousBookings.fxml");
    }

    /**
     * Changes current scene to MainMenu.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenu.fxml");
    }

    /**
     * Changes current scene to CampusMap.fxml.
     * @throws IOException input will be valid.
     */
    public void goToCampusMap() throws IOException {
        ApplicationDisplay.changeScene("/CampusMap.fxml");
    }

    /**
     * Changes current scene to MainMenu.fxml.
     * @throws IOException input will be valid.
     */
    public void goToMyAccount() throws IOException {
        ApplicationDisplay.changeScene("/MyAccountScene.fxml");
    }
}