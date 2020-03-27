package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;
import nl.tudelft.oopp.demo.views.PersonalCalendarView;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "mainMenu.fxml" file
 */
public class MainMenuController {

    /**
     * Handles the clicking of the calendar icon.
     * @throws IOException when it fails
     */
    public void calendarIcon() throws IOException {
        ApplicationDisplay.showCalendarScene(new PersonalCalendarView());
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
