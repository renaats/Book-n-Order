package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "myPreviousBookings.fxml" file
 */
public class MyPastBookingsController {

    /**
     * Changes to myCurrentBookings.fxml.
     * @throws IOException input will be valid.
     */
    public void myCurrentBookings() throws IOException {
        ApplicationDisplay.changeScene("/myCurrentBookings.fxml");
    }

    /**
     * Changes to myPreviousBookings.fxml.
     * @throws IOException input will be valid.
     */
    public void myPreviousBookings() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousBookings.fxml");
    }

    /**
     * Changes to myPreviousBikeReservations.fxml.
     * @throws IOException input will be valid.
     */
    public void myPreviousBikeReservations() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousTenBikeReservations.fxml");
    }

    /**
     * Changes to myPreviousRoomBookings.fxml.
     * @throws IOException input will be valid.
     */
    public void myPreviousRoomBookings() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousTenRoomBookings.fxml");
    }

    /**
     * Changes to myPreviousFoodOrders.fxml.
     * @throws IOException input will be valid.
     */
    public void myPreviousFoodOrders() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousTenFoodOrders.fxml");
    }

    /**
     *  method changes the view to that of the main menu
     * @throws IOException the method will never throw an exception
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/mainMenu.fxml");
    }

    /**
     * method changes view to main menu
     * @throws IOException should never be thrown
     */
    public void goToMyAccount() throws IOException {
        ApplicationDisplay.changeScene("/myAccountScene.fxml");
    }
}
