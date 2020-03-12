package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;


public class MainMenuController {

    /**
     * Handels the clicking of the calendar icon.
     * @throws IOException when it fails
     */
    public void calendarIcon(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/calendar.fxml");
    }
    /**
     * Handels the clicking of the Profile icon.
     * @throws IOException when it fails
     */

    public void goToProfile(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/myAccountScene.fxml");
    }
    /**
     * Handels the clicking of the Bookings icon.
     * @throws IOException when it fails
     */

    public void goToMainReservationsMenu(MouseEvent mouseEvent) throws IOException {
        ApplicationDisplay.changeScene("/MainMenuReservations.fxml");
    }







    public void myAccountScene(ActionEvent actionEvent) {

    }

    public void bookRoom(ActionEvent actionEvent) {
    }

    public void rentBike(ActionEvent actionEvent) {
    }

    public void orderFood(ActionEvent actionEvent) {
    }

    public void myCurrentBookings(ActionEvent actionEvent) {
    }

    public void myPreviousBookings(ActionEvent actionEvent) {
    }

    public void template(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/Template.fxml");
    }


}
