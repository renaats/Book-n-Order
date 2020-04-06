package nl.tudelft.oopp.demo.controllers.generic;

import java.io.IOException;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "MyCurrentBookings.fxml" file
 */
public class MyCurrentBookingsController {

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
     * Changes current scene to MyCurrentBikeReservations.fxml.
     * @throws IOException input will be valid.
     */
    public void myCurrentBikeReservations() throws IOException {
        ApplicationDisplay.changeScene("/MyCurrentBikeReservations.fxml");
    }

    /**
     * Changes current scene to MyCurrentRoomReservations.fxml.
     * @throws IOException input will be valid.
     */
    public void myCurrentRoomBookings() throws IOException {
        ApplicationDisplay.changeScene("/MyCurrentRoomReservations.fxml");
    }

    /**
     * Changes current scene to MyCurrentFoodReservations.fxml.
     * @throws IOException input will be valid.
     */
    public void myCurrentFoodOrders() throws IOException {
        ApplicationDisplay.changeScene("/MyCurrentFoodReservations.fxml");
    }

    /**
     * Changes current scene to MainMenu.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenu.fxml");
    }

    /**
     * method changes view to main menu
     * @throws IOException should never throw an exception
     */
    public void goToMyAccount() throws IOException {
        ApplicationDisplay.changeScene("/MyAccountScene.fxml");
    }
}