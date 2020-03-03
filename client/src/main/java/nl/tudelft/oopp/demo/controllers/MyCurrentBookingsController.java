package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;

import java.io.IOException;

public class MyCurrentBookingsController {
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
     * Changes to myCurrentBikeReservations.fxml.
     * @throws IOException
     */
    public void myCurrentBikeReservations() throws IOException {
        ApplicationDisplay.changeScene("/myCurrentBikeReservations.fxml");
    }

    /**
     * Changes to myCurrentRoomBookings.fxml.
     * @throws IOException
     */
    public void myCurrentRoomBookings() throws IOException {
        ApplicationDisplay.changeScene("/myCurrentRoomBookings.fxml");
    }

    /**
     * Changes to myCurrentFoodOrders.fxml.
     * @throws IOException
     */
    public void myCurrentFoodOrders() throws IOException {
        ApplicationDisplay.changeScene("/myCurrentFoodOrders.fxml");
    }

    /**
     * Changes to mainScene.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/templateScene.fxml");
    }
}