package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class MainMenuController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Changes to templateScene.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/templateScene.fxml");
    }

    /**
     * Changes to myCurrentBookings.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myCurrentBookings() throws IOException {
        ApplicationDisplay.changeScene("/myCurrentBookings.fxml");
    }

    /**
     * Changes to myPreviousBookings.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myPreviousBookings() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousBookings.fxml");
    }

    public void myAccountScene() throws IOException {
        ApplicationDisplay.changeScene("/myAccountScene.fxml");
    }

    public void myPreviousTenRoomBookings() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousTenRoomBookings.fxml");
    }

    /**
     * Changes to myPreviousTenBikeReservations.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myPreviousTenBikeReservations() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousTenBikeReservations.fxml");
    }

    /**
     * Changes to myPreviousTenFoodOrders.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myPreviousTenFoodOrders() throws IOException {
        ApplicationDisplay.changeScene("/myPreviousTenFoodOrders.fxml");
    }

    public void myCurrentBikeReservations() throws IOException {
        ApplicationDisplay.changeScene("/myCurrentBikeReservations.fxml");
    }

    /**
     * Changes to myCurrentRoomBookings.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myCurrentRoomBookings() throws IOException {
        ApplicationDisplay.changeScene("/myCurrentRoomBookings.fxml");
    }

    /**
     * Changes to myCurrentFoodOrders.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myCurrentFoodOrders() throws IOException {
        ApplicationDisplay.changeScene("/myCurrentFoodOrders.fxml");
    }
}
