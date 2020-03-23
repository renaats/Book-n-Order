package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;


public class MainMenuController {

    /**
     * Handles the clicking of the calendar icon.
     * @throws IOException when it fails
     */
    public void calendarIcon() throws IOException {
        ApplicationDisplay.changeScene("/calendar.fxml");
    }

    /**
     * Handles the clicking of the Profile icon.
     * @throws IOException when it fails
     */

    public void goToProfile() throws IOException {
        ApplicationDisplay.changeScene("/myAccountScene.fxml");
    }

    /**
     * Handles the clicking of the Bookings icon.
     * @throws IOException when it fails
     */
    public void goToMainReservationsMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenuReservations.fxml");
    }

}
