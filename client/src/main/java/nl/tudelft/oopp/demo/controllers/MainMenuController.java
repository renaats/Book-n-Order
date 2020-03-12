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
    public void calendarIcon() throws IOException {
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




    public void goToProfile() throws IOException {
        ApplicationDisplay.changeScene("/myAccountScene.fxml");
    }

    /**
     * Changes current scene to myCurrentBookings.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */

    public void goToMainReservationsMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenuReservations.fxml");
    }

    /**
     * testing method should be deleted
     * @param actionEvent
     * @throws IOException
     */
    public void template(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/Template.fxml");
    }

    public void calendarIcon(MouseEvent mouseEvent) {

    }

    public void myCurrentBookings(ActionEvent actionEvent) {
    }

    public void myPreviousBookings(ActionEvent actionEvent) {
    }

    public void template(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/Template.fxml");
    }


}
