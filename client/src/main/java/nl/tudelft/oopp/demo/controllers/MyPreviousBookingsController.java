package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class MyPreviousBookingsController {
    /**
     * Changes to myAccountScene.fxml.
     * @throws IOException will not be wrong.
     */
    public void myAccountScene() throws IOException {
        ApplicationDisplay.changeScene("/myAccountScene.fxml", null);
    }

    /**
     * Changes to myCurrentBookings.fxml.
     * @throws IOException will not be wrong.
     */
    public void myCurrentBookings() throws IOException {
        ApplicationDisplay.changeScene("/myCurrentBookings.fxml", null);
    }

    /**
     * Changes to myPreviousBookings.fxml.
     * @throws IOException input will not be invalid.
     */
    public void myPreviousBookings() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousBookings.fxml", null);
    }

    /**
     * Changes to myPreviousTenRoomBookings.fxml.
     * @throws IOException will not be invalid.
     */
    public void myPreviousTenRoomBookings() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousTenRoomBookings.fxml", null);
    }

    /**
     * Changes to myPreviousTenBikeReservations.fxml.
     * @throws IOException will not be valid.
     */
    public void myPreviousTenBikeReservations() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousTenBikeReservations.fxml", null);
    }

    /**
     * Changes to myPreviousTenFoodOrders.fxml.
     * @throws IOException will not be valid.
     */
    public void myPreviousTenFoodOrders() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousTenFoodOrders.fxml", null);
    }
}