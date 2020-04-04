package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "MyCurrentBikeReservations.fxml" file
 */
public class MyCurrentBikeReservationsController {

    /**
     * change the view to the main menu when the home icon is clicked
     * @throws IOException should never throw an exception
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenu.fxml");
    }

    /**
     * changes view to the user's current bookings view when the back arrow is clicked
     * @throws IOException should never throw an exception
     */
    public void goToMyCurrentReservations() throws IOException {
        ApplicationDisplay.changeScene("/MyCurrentBookings.fxml");
    }
}
