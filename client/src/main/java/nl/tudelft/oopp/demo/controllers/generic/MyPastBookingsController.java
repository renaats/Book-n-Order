package nl.tudelft.oopp.demo.controllers.generic;

import java.io.IOException;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "MyPreviousBookings.fxml" file
 */
public class MyPastBookingsController {

    /**
     * Changes to MyCurrentBookings.fxml.
     * @throws IOException input will be valid.
     */
    public void myCurrentBookings() throws IOException {
        ApplicationDisplay.changeScene("/MyCurrentBookings.fxml");
    }

    /**
     * Changes to MyPreviousBookings.fxml.
     * @throws IOException input will be valid.
     */
    public void myPreviousBookings() throws IOException {
        ApplicationDisplay.changeScene("/MyPreviousBookings.fxml");
    }

    /**
     * Changes to MyPreviousBikeReservations.fxml.
     * @throws IOException input will be valid.
     */
    public void myPreviousBikeReservations() throws IOException {
        ApplicationDisplay.changeScene("/MyPreviousBikeReservations.fxml");
    }

    /**
     * Changes to MyPreviousRoomBookings.fxml.
     * @throws IOException input will be valid.
     */
    public void myPreviousRoomBookings() throws IOException {
        ApplicationDisplay.changeScene("/MyPreviousRoomBookings.fxml");
    }

    /**
     * Changes to MyPreviousFoodOrders.fxml.
     * @throws IOException input will be valid.
     */
    public void myPreviousFoodOrders() throws IOException {
        ApplicationDisplay.changeScene("/MyPreviousFoodOrders.fxml");
    }

    /**
     *  method changes the view to that of the main menu
     * @throws IOException the method will never throw an exception
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenu.fxml");
    }

    /**
     * method changes view to main menu
     * @throws IOException should never be thrown
     */
    public void goToMyAccount() throws IOException {
        ApplicationDisplay.changeScene("/MyAccountScene.fxml");
    }
}
