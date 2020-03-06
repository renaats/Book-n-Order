package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class MyPreviousBookingsController {
    /**
     * Changes to myAccountScene.fxml.
     * @throws IOException
     */
    public void myAccountScene() throws IOException {
        ApplicationDisplay.changeScene("/myAccountScene.fxml");
    }

    /**
     * Changes to myCurrentBookings.fxml.
     * @throws IOException
     */
    public void myCurrentBookings() throws IOException {
        ApplicationDisplay.changeScene("/myCurrentBookings.fxml");
    }

    /**
     * Changes to myPreviousBookings.fxml.
     * @throws IOException
     */
    public void myPreviousBookings() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousBookings.fxml");
    }

    /**
     * Changes to myPreviousTenRoomBookings.fxml.
     * @throws IOException
     */
    public void myPreviousTenRoomBookings() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousTenRoomBookings.fxml");
    }

    /**
     * Changes to myPreviousTenBikeReservations.fxml.
     * @throws IOException
     */
    public void myPreviousTenBikeReservations() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousTenBikeReservations.fxml");
    }

    /**
     * Changes to myPreviousTenFoodOrders.fxml.
     * @throws IOException
     */
    public void myPreviousTenFoodOrders() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousTenFoodOrders.fxml");
    }
}