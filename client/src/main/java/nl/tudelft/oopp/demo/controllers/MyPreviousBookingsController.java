package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class MyPreviousBookingsController {
    /**
     * Changes to myAccountScene.fxml.
     * @throws IOException will not be wrong.
     */
    public void myAccountScene() throws IOException {
        ApplicationDisplay.changeScene("/myAccountScene.fxml");
    }

    /**
     * Changes to myCurrentBookings.fxml.
     * @throws IOException will not be wrong.
     */
    public void myCurrentBookings() throws IOException {
        ApplicationDisplay.changeScene("/myCurrentBookings.fxml");
    }

    /**
     * Changes to myPreviousBookings.fxml.
     * @throws IOException input will not be invalid.
     */
    public void myPreviousBookings() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousBookings.fxml");
    }

    /**
     * Changes to myPreviousTenRoomBookings.fxml.
     * @throws IOException will not be invalid.
     */
    public void myPreviousTenRoomBookings() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousTenRoomBookings.fxml");
    }

    /**
     * Changes to myPreviousTenBikeReservations.fxml.
     * @throws IOException will not be valid.
     */
    public void myPreviousTenBikeReservations() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousTenBikeReservations.fxml");
    }

    /**
     * Changes to myPreviousTenFoodOrders.fxml.
     * @throws IOException will not be valid.
     */
    public void myPreviousTenFoodOrders() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousTenFoodOrders.fxml");
    }

    /**
     *  method changes the view to that of the main menu
     * @param actionEvent clicking the button to go back to menu
     * @throws IOException the method will never throw an exception
     */
    public void mainMenu(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/mainMenuReservations.fxml");
    }
}