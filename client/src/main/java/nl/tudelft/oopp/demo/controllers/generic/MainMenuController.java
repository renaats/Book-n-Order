package nl.tudelft.oopp.demo.controllers.generic;

import java.io.IOException;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "MainMenu.fxml" file
 */
public class MainMenuController {

    /**
     * Handles the clicking of the calendar icon.
     */
    public void calendarIcon() throws IOException {
        ApplicationDisplay.changeScene("/PersonalCalendar.fxml");
    }

    /**
     * Handles the clicking of the Profile icon.
     * @throws IOException when it fails
     */
    public void goToProfile() throws IOException {
        ApplicationDisplay.changeScene("/MyAccountScene.fxml");
    }

    /**
     * Handles the clicking of the Bookings icon.
     * @throws IOException when it fails
     */
    public void goToMainReservationsMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenuReservations.fxml");
    }

    /**
     * Handles the clicking of the Contact info button.
     * @throws IOException when it fails
     */
    public void goToContactInformation() throws IOException {
        ApplicationDisplay.changeScene("/ContactInformation.fxml");
    }

    /**
     * Handles the clicking of the Building Information button.
     * @throws IOException when it fails
     */
    public void goToBuildingInformation() throws IOException {
        ApplicationDisplay.changeScene("/BuildingInformation.fxml");
    }
}