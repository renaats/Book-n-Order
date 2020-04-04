package nl.tudelft.oopp.demo.controllers.generic;

import java.io.IOException;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class MyPreviousBookingsController {

    /**
     * Changes current scene to MyCurrentBookings.fxml.
     * @throws IOException will not be wrong.
     */
    public void myCurrentBookings() throws IOException {
        ApplicationDisplay.changeScene("/MyCurrentBookings.fxml");
    }

    /**
     * Changes current scene to MyPreviousBookings.fxml.
     * @throws IOException input will not be invalid.
     */
    public void myPreviousBookings() throws IOException {
        ApplicationDisplay.changeScene("/MyPreviousBookings.fxml");
    }

    /**
     *  method changes the view to that of the main menu
     * @throws IOException the method will never throw an exception
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenuReservations.fxml");
    }
}
