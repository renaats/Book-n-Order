package nl.tudelft.oopp.demo.controllers.generic;

import java.io.IOException;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class MyPreviousBookingsController {

    /**
     * Changes current scene to myCurrentBookings.fxml.
     * @throws IOException will not be wrong.
     */
    public void myCurrentBookings() throws IOException {
        ApplicationDisplay.changeScene("/myCurrentBookings.fxml");
    }

    /**
     * Changes current scene to myPreviousBookings.fxml.
     * @throws IOException input will not be invalid.
     */
    public void myPreviousBookings() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousBookings.fxml");
    }

    /**
     *  method changes the view to that of the main menu
     * @throws IOException the method will never throw an exception
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/mainMenuReservations.fxml");
    }
}
